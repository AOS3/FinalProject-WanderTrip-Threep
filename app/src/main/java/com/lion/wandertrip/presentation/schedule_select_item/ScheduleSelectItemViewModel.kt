package com.lion.wandertrip.presentation.schedule_select_item

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.lion.wandertrip.TripApplication
import com.lion.wandertrip.model.ContentsModel
import com.lion.wandertrip.model.ScheduleItem
import com.lion.wandertrip.model.TripItemModel
import com.lion.wandertrip.service.TripScheduleService
import com.lion.wandertrip.service.UserService
import com.lion.wandertrip.util.ContentTypeId
import com.lion.wandertrip.util.RouletteScreenName
import com.lion.wandertrip.util.SharedTripItemList
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleSelectItemViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    val tripScheduleService: TripScheduleService,
    val userService: UserService
) : ViewModel() {

    val application = context as TripApplication

    // 여행지 추가할 날짜
    val scheduleDate = mutableStateOf<Timestamp>(Timestamp.now())
    // 일정 Doc Id
    val tripScheduleDocId = mutableStateOf("")

    // 여행지 항목 리스트
    val tripItemList = mutableStateListOf<TripItemModel>()

    // 🔽 로딩 상태 추가
    val isLoading = mutableStateOf(false)

    // 관심 지역 목록
    val userLikeList = mutableStateListOf<String>()

    // 일정 리뷰 관련 리스트
    val contentsList = mutableStateListOf<ContentsModel>()


    // 이전 화면 으로 이동 (일정 상세 화면)
    fun backScreen() {
        application.navHostController.popBackStack()
    }

    // 유저 관심 지역 옵저브
    fun observeUserLikeList() {
        val firestore = FirebaseFirestore.getInstance()
        val userDocId = application.loginUserModel.userDocId
        val userLikeCollectionRef = firestore.collection("UserData")
            .document(userDocId)
            .collection("UserLikeList")

        userLikeCollectionRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("observeUserLikeList", "Error: ${error.message}")
                return@addSnapshotListener
            }
            if (snapshot != null) {
                // 각 문서의 "contentId" 값을 추출합니다.
                val likeItems = snapshot.documents.mapNotNull { doc ->
                    doc.getString("contentId")
                }
                // 기존 리스트를 클리어하고 최신 값으로 업데이트합니다.
                userLikeList.clear()
                userLikeList.addAll(likeItems)
                Log.d("observeUserLikeList", "userLikeList updated: $userLikeList")
            }
        }
    }

    // 리뷰 데이터 컬렉션 옵저브
    fun observeContentsData() {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("ContentsData")
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    Log.e("observeContentsData", "데이터 옵저브 에러: ${error.message}")
                    return@addSnapshotListener
                }
                querySnapshot?.let { snapshot ->
                    val resultContentsList = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(ContentsModel::class.java)
                    }
                    // 기존 리스트를 클리어하고 최신 데이터로 업데이트
                    contentsList.clear()
                    contentsList.addAll(resultContentsList)
                    Log.d("observeContentsData", "총 ${contentsList.size}개의 문서를 가져왔습니다.")
                }
            }
    }



    // 여행지 항목 가져 오기
    fun loadTripItems(serviceKey: String, areaCode: String, contentTypeId: String) {
        viewModelScope.launch {
            isLoading.value = true // ✅ 로딩 시작

            val tripItems = async(Dispatchers.IO) {
                tripScheduleService.loadTripItems(serviceKey, areaCode, contentTypeId)
            }.await()

            if (tripItems != null) {
                SharedTripItemList.sharedTripItemList.clear()
                SharedTripItemList.sharedTripItemList.addAll(tripItems)
            }

            isLoading.value = false // ✅ 로딩 완료
        }
    }

    // 관심 지역 추가, 관심 지역 카운트 증가
    fun addLikeItem(likeItemContentId: String) {
        viewModelScope.launch {
            val work1 = async(Dispatchers.IO) {
                userService.addLikeItem(application.loginUserModel.userDocId, likeItemContentId)
            }

            val work2 = async(Dispatchers.IO) {
                userService.addLikeCnt(likeItemContentId)
            }
        }
    }

    // 관심 지역 삭제, 관심 지역 카운트 감소
    fun removeLikeItem(likeItemContentId: String) {
        viewModelScope.launch {
            val work1 = async(Dispatchers.IO) {
                userService.removeLikeItem(application.loginUserModel.userDocId, likeItemContentId)
            }

            val work2 = async(Dispatchers.IO) {
                userService.removeLikeCnt(likeItemContentId)
            }
        }
    }

    // 일정에 여행지 항목 추가
    fun addTripItemToSchedule(tripItemModel: TripItemModel) {

        viewModelScope.launch {
            val work1 = async(Dispatchers.IO) {
                val scheduleItem = ScheduleItem(
                    itemTitle = tripItemModel.title,
                    itemType = when(tripItemModel.contentTypeId) {
                        ContentTypeId.TOURIST_ATTRACTION.contentTypeCode.toString() -> "관광지"
                        ContentTypeId.RESTAURANT.contentTypeCode.toString() -> "음식점"
                        ContentTypeId.ACCOMMODATION.contentTypeCode.toString() -> "숙소"
                        else -> ""
                    },
                    itemDate = scheduleDate.value,
                    itemLongitude = tripItemModel.mapLong,
                    itemLatitude = tripItemModel.mapLat,
                    itemContentId = tripItemModel.contentId,
                )

                tripScheduleService.addTripItemToSchedule(tripScheduleDocId.value, scheduleDate.value, scheduleItem)
            }.await()
            application.navHostController.popBackStack()
        }
    }

    // 룰렛 화면으로 이동
    fun moveToRouletteItemScreen(tripScheduleDocId: String, areaName: String, areaCode: Int) {
        application.navHostController.navigate(
            "${RouletteScreenName.ROULETTE_ITEM_SCREEN.name}?" +
                    "tripScheduleDocId=${tripScheduleDocId}&areaName=${areaName}&areaCode=${areaCode}&scheduleDate=${scheduleDate.value.seconds}"
        )
    }



}