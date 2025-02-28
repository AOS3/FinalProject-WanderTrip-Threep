package com.lion.wandertrip.presentation.bottom.trip_note_page

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.lion.wandertrip.TripApplication
import com.lion.wandertrip.model.TripNoteModel
import com.lion.wandertrip.service.TripNoteService
import com.lion.wandertrip.util.MainScreenName
import com.lion.wandertrip.util.TripNoteScreenName
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okhttp3.internal.toImmutableList
import javax.inject.Inject

@HiltViewModel
class TripNoteViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    val tripNoteService: TripNoteService,
) : ViewModel() {

    val tripApplication = context as TripApplication

    // TopAppBar의 타이틀
    val topAppBarTitle = mutableStateOf("")

    // 글 목록을 구성하기 위한 상태 관리 변수
    var tripNoteList = mutableStateListOf<TripNoteModel>()

    // 보여줄 이미지의 Uri
    //var showImageUri = mutableStateListOf<Uri?>(null)

    val imageUrisMap = mutableStateMapOf<Int, MutableList<Uri?>>()

    // + 버튼(fab 버튼)을 눌렀을 때
    fun addButtonOnClick(){
        val scheduleTitle = "" // 기본값 설정
        // 여행기 추가하는 화면으로 이동
        tripApplication.navHostController.navigate("${TripNoteScreenName.TRIP_NOTE_WRITE.name}/$scheduleTitle/")
    }

    // 각 항목을 눌렀을 때
    fun listItemOnClick(documentId : String){
        // 여행기 상세보기 화면으로 이동 (각 항목의 문서 id를 전달... 추후에)
        tripApplication.navHostController.navigate("${TripNoteScreenName.TRIP_NOTE_DETAIL.name}/${documentId}")
    }


    fun gettingTripNoteData() {
        CoroutineScope(Dispatchers.Main).launch {
            val work1 = async(Dispatchers.IO) {
                tripNoteService.gettingTripNoteList()
            }

            val recyclerViewList = work1.await().mapIndexed { index, tripNoteModel ->
                index to tripNoteModel
            }

            val storage = Firebase.storage // Firebase Storage 인스턴스 가져오기
            val storageReference = storage.reference

            recyclerViewList.forEach { (index, tripNoteModel) ->
                val imagePaths = tripNoteModel.tripNoteImage ?: emptyList() // null일 경우 빈 리스트 처리
                val imageUris = mutableListOf<Uri?>()

                // Firebase에서 이미지를 동기적으로 다운로드
                imagePaths.forEach { imagePath ->
                    val imageRef = storageReference.child("image/$imagePath")
                    try {
                        val uri = imageRef.downloadUrl.await() // `await`를 사용해 비동기 작업 완료 대기
                        imageUris.add(uri)
                    } catch (e: Exception) {
                        Log.e("FirebaseStorage", "Failed to get download URL for $imagePath", e)
                    }
                }

                // 모든 이미지를 로드한 후 map에 추가
                imageUrisMap[index] = imageUris

                // 로그로 확인
                Log.d("TripNoteScreen", "Index: $index, imageUris: ${imageUrisMap[index]}")
            }

            // 상태 관리 변수에 담아준다.
            tripNoteList.clear()
            tripNoteList.addAll(recyclerViewList.map { it.second })
        }
    }


}