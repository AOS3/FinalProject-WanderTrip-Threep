package com.lion.wandertrip.presentation.bottom.home_page

import android.content.Context
import android.system.Os.remove
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.lion.wandertrip.service.TripNoteService
import com.lion.wandertrip.TripApplication
import com.lion.wandertrip.model.TripItemModel
import com.lion.wandertrip.model.TripNoteModel
import com.lion.wandertrip.model.UserModel
import com.lion.wandertrip.service.TripAreaBaseItemService
import com.lion.wandertrip.service.TripScheduleService
import com.lion.wandertrip.util.BotNavScreenName
import com.lion.wandertrip.service.UserService
import com.lion.wandertrip.util.MainScreenName
import com.lion.wandertrip.util.TripNoteScreenName
import com.lion.wandertrip.vo.TripNoteVO
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext context: Context,
    val tripNoteService: TripNoteService,
    val tripAreaBaseItemService: TripAreaBaseItemService,
    val userService: UserService
) : ViewModel(){

    val tripApplication = context as TripApplication

    val userLikeList = mutableStateOf(tripApplication.loginUserModel.userLikeList)

    // ✅ 사용자 정보 (LiveData로 관리하여 UI에서 감지할 수 있도록 변경)
    private val _userModel = MutableLiveData<UserModel>()
    val userModel: LiveData<UserModel> get() = _userModel

    // ✅ 좋아요 리스트 (Compose에서 감지 가능하도록 관리)
    val userLikeListCompose = mutableStateOf(listOf<String>())

    init {
        fetchUserData() // ✅ ViewModel 초기화 시 사용자 데이터 로드
    }

    private val _topScrapedTrips = MutableLiveData<List<TripNoteModel>>()
    val topScrapedTrips: LiveData<List<TripNoteModel>> get() = _topScrapedTrips

    private val _tripNoteList = MutableLiveData<List<TripNoteModel>>()
    val tripNoteList: LiveData<List<TripNoteModel>> get() = _tripNoteList

    private val _imageUrlMap = mutableStateMapOf<String, String?>()
    val imageUrlMap: Map<String, String?> get() = _imageUrlMap

    private val _randomTourItems = MutableLiveData<List<TripItemModel>>() // ✅ LiveData 추가
    val randomTourItems: LiveData<List<TripItemModel>> get() = _randomTourItems

    private val _isLoading = MutableLiveData(false) // ✅ 로딩 상태 추가
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var isFetched = false // 🔥 데이터가 로드되었는지 여부를 저장

    // 🔥 Firestore에서 사용자 정보 가져오기
    private fun fetchUserData() {
        viewModelScope.launch {
            val userDocId = tripApplication.loginUserModel.userDocId
            val userLikeListFromFirestore = userService.gettingUserLikeList(userDocId)

            _userModel.value = UserModel(userDocId = userDocId, userLikeList = userLikeListFromFirestore)
            userLikeList.value = userLikeListFromFirestore // ✅ 좋아요 리스트 초기화
        }
    }

    // 🔥 무작위 관광지 데이터를 가져오는 함수
    fun fetchRandomTourItems() {
        if (isFetched) return // 이미 데이터가 로드되었다면 다시 호출하지 않음

        viewModelScope.launch {
            _isLoading.value = true // ✅ 검색 시작 전 로딩 시작
            val items = tripAreaBaseItemService.getTripAreaBaseItem()
            _randomTourItems.value = items ?: emptyList()
            _isLoading.value = false // ✅ 검색 시작 전 로딩 시작
            isFetched = true // ✅ 데이터 로드 완료 표시
        }
    }

    fun toggleFavorite(contentId: String) {
        viewModelScope.launch {
            val userDocId = _userModel.value?.userDocId ?: return@launch // ✅ userModel이 null이면 실행하지 않음

            val isLiked = userLikeList.value.contains(contentId)

            // ✅ 기존 리스트를 변경하지 않고 새로운 리스트 객체 생성
            val updatedList = if (isLiked) {
                userLikeList.value.filter { it != contentId } // ✅ 리스트에서 제거
            } else {
                userLikeList.value + contentId // ✅ 리스트에 추가
            }

            // ✅ UI 상태 즉시 반영 (새로운 리스트 객체 할당)
            userLikeList.value = updatedList

            // ✅ Firestore에 업데이트 (비동기적으로 수행)
            userService.updateUserLikeList(userDocId, updatedList)

            // ✅ _userModel의 값을 변경하여 Compose가 감지하도록 설정
            _userModel.value = _userModel.value?.let { userModel ->
                UserModel(
                    userDocId = userModel.userDocId,
                    userLikeList = updatedList // ✅ 새로운 리스트 객체 할당
                )
            }
        }
    }

    fun fetchTripNotes() {
        viewModelScope.launch {
            try {
                val firestore = FirebaseFirestore.getInstance()
                val collectionReference = firestore.collection("TripNoteData")

                val result = collectionReference
                    .orderBy("tripNoteTimeStamp", Query.Direction.DESCENDING)
                    .get()
                    .await()

                val tripNotes = result.documents.mapNotNull { document ->
                    val tripNoteVO = document.toObject(TripNoteVO::class.java)
                    tripNoteVO?.toTripNoteModel(document.id)
                }

                _tripNoteList.value = tripNotes // ✅ Firestore 데이터 업데이트
                fetchImageUrls() // ✅ 여행기 데이터 가져온 후 이미지 URL도 가져오기

            } catch (e: Exception) {
                Log.e("Firestore", "Error fetching trip notes", e)
            }
        }
    }

    fun fetchImageUrls() {
        val tripNotes = tripNoteList.value ?: return // 🔥 LiveData에서 최신 데이터를 가져옴

        tripNotes.forEach { tripNote ->
            val fileName = tripNote.tripNoteImage.firstOrNull() ?: return@forEach

            // 이미 로딩 중이거나 가져온 데이터가 있으면 다시 요청하지 않음
            if (_imageUrlMap.containsKey(fileName)) return@forEach

            // 🔥 초기 로딩 상태를 빈 문자열("")로 설정하여 Compose가 감지할 수 있도록 변경
            _imageUrlMap[fileName] = ""

            viewModelScope.launch {
                val imageUrl = tripNoteService.gettingImage(fileName)
                _imageUrlMap[fileName] = imageUrl?.toString() ?: ""  // 🚀 URL이 null이면 빈 문자열로 처리
            }
        }
    }

    fun getTopScrapedTrips() {
        viewModelScope.launch {
            val tripNotes = tripNoteService.gettingTripNoteListWithScrapCount()
            val top3List = tripNotes.sortedByDescending { it.tripNoteScrapCount }
                .take(3) // ✅ 스크랩 수 기준 상위 3개 추출
            _topScrapedTrips.value = top3List
        }
    }

    fun fetchTripNoteListWithScrapCount() {
        viewModelScope.launch {
            val tripNotes = tripNoteService.gettingTripNoteListWithScrapCount()
            _tripNoteList.value = tripNotes
        }
    }

    fun backScreen() {
        tripApplication.navHostController.popBackStack()
    }

    // 내 리뷰 화면 전환
    fun onClickIconSearch() {
        tripApplication.navHostController.navigate(MainScreenName.MAIN_SCREEN_SEARCH.name)
    }

    fun onClickTrip(contentId: String) {
        tripApplication.navHostController.navigate("${MainScreenName.MAIN_SCREEN_DETAIL.name}/$contentId")
    }

    fun onClickTripNote(documentId : String) {
        tripApplication.navHostController.navigate("${TripNoteScreenName.TRIP_NOTE_DETAIL.name}/${documentId}")
    }

}