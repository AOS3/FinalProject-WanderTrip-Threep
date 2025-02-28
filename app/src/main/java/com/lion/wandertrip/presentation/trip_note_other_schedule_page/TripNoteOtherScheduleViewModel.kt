package com.lion.wandertrip.presentation.trip_note_other_schedule_page

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.lion.wandertrip.TripApplication
import com.lion.wandertrip.model.TripNoteModel
import com.lion.wandertrip.model.TripScheduleModel
import com.lion.wandertrip.model.UserModel
import com.lion.wandertrip.service.TripNoteService
import com.lion.wandertrip.util.TripNoteScreenName
import com.lion.wandertrip.vo.TripNoteVO
import com.lion.wandertrip.vo.TripScheduleVO
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.concurrent.CountDownLatch
import javax.inject.Inject


@HiltViewModel
class TripNoteOtherScheduleViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    val tripNoteService: TripNoteService,
) : ViewModel() {

    // 여행기 데이터 리스트
    var tripNoteOtherScheduleList = mutableStateListOf<TripNoteModel?>()

    // 해당 여행기의 일정 데이터 리스트
    var tripNoteOtherScheduleAList = mutableStateListOf<TripScheduleModel?>()

    // 여행기 문서 id 리스트
    var tripNoteOtherScheduleDocIdList = mutableStateListOf<String?>()

    // 보여줄 이미지의 Uri
    val showImageUri = mutableStateOf<Uri?>(null)

    val tripApplication = context as TripApplication

    // 리사이클러뷰 데이터 리스트 (선택한 사람의 여행기 리스트)
    fun gettingTripNoteScheduleData(otherNickName : String) {

        CoroutineScope(Dispatchers.Main).launch {
            val work1 = async(Dispatchers.IO){
                tripNoteService.gettingOtherTripNoteList(otherNickName)
            }

            val recyclerViewList = work1.await().mapIndexed { index, tripNoteModel ->
                index to tripNoteModel
            }


            // 상태 관리 변수에 담아준다.
            tripNoteOtherScheduleList.clear()
            // tripNoteOtherScheduleList.addAll(recyclerViewList)
            tripNoteOtherScheduleList.addAll(recyclerViewList.map { it.second })

            // tripNoteOtherScheduleDocIdList에 documentId만 추출하여 담기
            tripNoteOtherScheduleDocIdList.clear()
            tripNoteOtherScheduleDocIdList.addAll(recyclerViewList.map { it.second.tripNoteDocumentId }.toList())
            // tripNoteOtherScheduleDocIdList에 값이 제대로 들어갔는지 확인
            Log.d("TripNote", "Document IDs: ${tripNoteOtherScheduleDocIdList.toList()}")


        }
    }



    // 프로필 사진 가져오기
    fun gettingTripNoteProfileData(otherNickName : String){

        CoroutineScope(Dispatchers.Main).launch {
            val work1 = async(Dispatchers.IO){
                tripNoteService.gettingOtherProfileList(otherNickName)
            }

            val imageUri = work1.await()

            // 이미지 URI 값 확인을 위한 로그
            Log.d("TripNoteProfile", "Retrieved Image URI: $imageUri")
            showImageUri.value  = work1.await()

        }
    }

    // 여행기 목록 클릭하면 상세 여행기로 이동하는 메서드
    fun goTripNoteDocId(documentId: String?) {
        // documentId가 null인지 체크
        if (documentId != null) {
            tripApplication.navHostController.navigate("${TripNoteScreenName.TRIP_NOTE_DETAIL.name}/${documentId}")
        } else {
            Log.e("TripNote", "Document ID is null. Navigation aborted.")
            // 필요한 경우 사용자에게 알림을 표시할 수 있음
        }
    }




    // 여행기에서 일정 정보 가져오기
    fun getTripScheduleByDocumentId(documentId: String): TripScheduleModel? {
        var result: TripScheduleModel? = null
        val latch = CountDownLatch(1) // CountDownLatch로 비동기 작업 완료 대기

        CoroutineScope(Dispatchers.IO).launch {
            val work1 = async {
                tripNoteService.gettingScheduleById(documentId)
            }
            result = work1.await() // 결과를 가져옴

            tripNoteOtherScheduleAList.clear()
            tripNoteOtherScheduleAList.add(result)

            latch.countDown() // 작업이 끝났음을 알림
        }

        latch.await() // 작업이 끝날 때까지 대기
        return result // 작업 완료 후 반환
    }







    // 뒤로 가기 버튼
    fun navigationButtonClick(){
        tripApplication.navHostController.popBackStack()
    }


    // ✅ Timestamp -> "YYYY.MM.DD" 형식 변환 함수
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTimestampToDateString(timestamp: Timestamp): String {
        val localDate = Instant.ofEpochMilli(timestamp.seconds * 1000)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd") // ✅ 년-월-일 포맷 적용
        return localDate.format(formatter)
    }

}