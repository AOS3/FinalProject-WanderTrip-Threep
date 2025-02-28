package com.lion.wandertrip.presentation.trip_note_write_page

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.lion.wandertrip.TripApplication
import com.lion.wandertrip.model.TripNoteModel
import com.lion.wandertrip.service.TripNoteService
import com.lion.wandertrip.util.BotNavScreenName
import com.lion.wandertrip.util.MainScreenName
import com.lion.wandertrip.util.Tools
import com.lion.wandertrip.util.TripNoteScreenName
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TripNoteWriteViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    val tripNoteService: TripNoteService
) : ViewModel() {


    val tripApplication = context as TripApplication

    // 프로그레스 상태
    val isProgressVisible = mutableStateOf(false)

    // TopAppBar의 타이틀
    val topAppBarTitle = mutableStateOf("여행기 작성")

    // 여행기 제목, 에러
    var tripNoteTitle = mutableStateOf("")
    val tripNoteTitleError = mutableStateOf("")
    val tripNoteTitleIsError = mutableStateOf(false)

    // 여행기 내용, 에러
    val tripNoteContent = mutableStateOf("")
    val tripNoteContentError = mutableStateOf("")
    val tripNoteContentIsError = mutableStateOf(false)

    // 일정 제목
    var tripScheduleTitle = mutableStateOf("")

    // 일정 문서 id
    var scheduleDocId = ("")


    // Bitmap
    val tripNotePreviewBitmap = mutableStateListOf<Bitmap?>()

    // 저장할 이미지 경로 리스트
    val tripNoteImages = mutableStateListOf<String>()


    // 뒤로 가기 버튼
    fun navigationButtonClick(){
        tripApplication.navHostController.popBackStack()
    }

    // 일정 추가 버튼
    fun addTripScheduleClick(){
        // 일정 선택 화면으로 이동
        tripApplication.navHostController.navigate(TripNoteScreenName.TRIP_NOTE_SCHEDULE.name)
    }

    // 사진 삭제
    fun removeTripNoteImage(bitmap: Bitmap) {
        tripNotePreviewBitmap.remove(bitmap)
    }

    // 게시하기 버튼
    fun tripNoteDoneClick(){
        isProgressVisible.value = true


        val tripNoteTitle = tripNoteTitle.value
        val tripNoteContent = tripNoteContent.value
        var tripNoteImage = "none"
        val tripNoteTimeStamp = Timestamp.now()


        // 로그인한 사용자의 닉네임
        val userNickname = tripApplication.loginUserModel.userNickName

        // 저장
        CoroutineScope(Dispatchers.Main).launch {



            // 이미지가 첨부되어 있다면
            if (tripNotePreviewBitmap != null) {

                for (bitmap in tripNotePreviewBitmap) {

                    // 서버상에서의 파일 이름
                    tripNoteImage = "image_${System.currentTimeMillis()}.jpg"
                    // 로컬에 ImageView에 있는 이미지 데이터를 저장한다.
                    Tools.saveBitmap(tripApplication, bitmap!!)

                    val work1 = async(Dispatchers.IO) {
                        // 외부 저장소의 경로를 가져온다.
                        val filePath = tripApplication.getExternalFilesDir(null).toString()
                        tripNoteService.uploadTripNoteImage("${filePath}/uploadTemp.jpg", tripNoteImage)
                    }
                    work1.join()

                    // 이미지 경로를 리스트에 추가
                    tripNoteImages.add(tripNoteImage)
                }
            }

            // 서버에 저장할 여행기 데이터
            val tripNoteModel = TripNoteModel()
            tripNoteModel.tripNoteTitle = tripNoteTitle
            tripNoteModel.tripNoteContent = tripNoteContent
            tripNoteModel.tripNoteImage = tripNoteImages
            tripNoteModel.tripNoteTimeStamp = tripNoteTimeStamp
            tripNoteModel.tripScheduleDocumentId = scheduleDocId
            tripNoteModel.userNickname = userNickname

            try {
            // 저장하기
            val work2 = async(Dispatchers.IO){
                tripNoteService.addTripNoteData(tripNoteModel)
            }
            val documentId = work2.await()

            // 프로그레스 바 숨기기
            isProgressVisible.value = false

            // 여행기 리스트 화면으로 이동
            tripApplication.navHostController.popBackStack()
            // 여행기 상세에 documentId 전달하기
            tripApplication.navHostController.navigate("${TripNoteScreenName.TRIP_NOTE_DETAIL.name}/${documentId}")
                tripApplication.navHostController.popBackStack()


        } catch (e: Exception) {
            Log.e("TripNote", "Error saving trip note", e)
            isProgressVisible.value = false
            // 에러 메시지 표시 (옵션)
        }
        }

    }

}