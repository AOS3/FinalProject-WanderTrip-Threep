package com.lion.wandertrip.presentation.bottom.schedule_page

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.lion.wandertrip.TripApplication
import com.lion.wandertrip.model.TripScheduleModel
import com.lion.wandertrip.service.TripScheduleService
import com.lion.wandertrip.util.AreaCode
import com.lion.wandertrip.util.ScheduleScreenName
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    val tripScheduleService : TripScheduleService
) : ViewModel() {

    val application = context as TripApplication
    
    // 일정 데이터
    var tripScheduleList = mutableStateListOf<TripScheduleModel>()

    // 유저 일정 DocId 리스트
    val userScheduleDocIdList = mutableStateListOf<String>()
    // 유저 일정 리스트
    val userScheduleList = mutableStateListOf<TripScheduleModel>()

    // 초대 받은 일정 DocId 리스트
    val invitedScheduleDocIdList = mutableStateListOf<String>()
    // 초대 받은 일정 리스트
    val invitedScheduleList = mutableStateListOf<TripScheduleModel>()

    // 유저 일정 리스트들 옵저버
    fun observeUserScheduleDocIdList() {
        val firestore = FirebaseFirestore.getInstance()
        // application.loginUserModel.userDocId 를 통해 유저 문서 ID 획득 (null 아님을 가정)
        val userDocId = application.loginUserModel.userDocId
        val userDocRef = firestore.collection("UserData").document(userDocId)

        // 문서 변경 감지 리스너 등록
        userDocRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("observeUserData", "Error: ${error.message}")
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                // userScheduleList 필드를 List<String> 형태로 가져오기 (없으면 빈 리스트)
                val schedule = snapshot.get("userScheduleList") as? List<String> ?: emptyList()
                // invitedScheduleList 필드를 List<String> 형태로 가져오기 (없으면 빈 리스트)
                val invited = snapshot.get("invitedScheduleList") as? List<String> ?: emptyList()

                // 기존 리스트 클리어 후 업데이트
                userScheduleDocIdList.clear()
                userScheduleDocIdList.addAll(schedule)

                // 기존 리스트 클리어 후 업데이트
                invitedScheduleDocIdList.clear()
                invitedScheduleDocIdList.addAll(invited)

                // 유저 일정 docId로 일정 항목 가져 오기
                fetchUserScheduleList()
                // 초대 받은 일정 docId로 일정 항목 가져 오기
                fetchInvitedScheduleList()
            }
        }
    }

    // 유저 일정 docId로 일정 항목 가져 오기
    fun fetchUserScheduleList() {
        viewModelScope.launch {
            val work1 = async(Dispatchers.IO) {
                tripScheduleService.fetchScheduleList(userScheduleDocIdList)
            }.await()

            userScheduleList.clear()
            userScheduleList.addAll(work1)

            userScheduleList.forEach {
                Log.d("ScheduleViewModel2", "userScheduleList: ${it.scheduleTitle}")
            }
        }
    }

    // 초대 받은 일정 docId로 일정 항목 가져 오기
    fun fetchInvitedScheduleList() {
        viewModelScope.launch {
            val work1 = async(Dispatchers.IO) {
                tripScheduleService.fetchScheduleList(invitedScheduleDocIdList)
            }.await()

            invitedScheduleList.clear()
            invitedScheduleList.addAll(work1)

            invitedScheduleList.forEach {
                Log.d("ScheduleViewModel2", "invitedScheduleList: ${it.scheduleTitle}")
            }
        }
    }

    // 일정 추가 버튼 클릭 이벤트
    fun addIconButtonEvent() {
        application.navHostController.navigate( "${ScheduleScreenName.SCHEDULE_ADD_SCREEN.name}/")
    }

    // ✅ Timestamp -> "YYYY.MM.DD" 형식 변환 함수
    fun formatTimestampToDateString(timestamp: Timestamp): String {
        val localDate = Instant.ofEpochMilli(timestamp.seconds * 1000)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd") // ✅ 년-월-일 포맷 적용
        return localDate.format(formatter)
    }

    // 내 일정 삭제
    fun removeUserSchedule(tripScheduleDocId: String) {
        Log.d("ScheduleViewModel", "deleteSchedule: $tripScheduleDocId")
        viewModelScope.launch {
            val work1 = async(Dispatchers.IO) {
                tripScheduleService.removeUserScheduleList(
                    application.loginUserModel.userDocId,
                    tripScheduleDocId
                )
            }.await()

            val work2 = async(Dispatchers.IO) {
                tripScheduleService.removeScheduleInviteList(
                    tripScheduleDocId,
                    application.loginUserModel.userDocId
                )
            }.await()
        }
    }

    // 초대 받은 일정 삭제
    fun removeInvitedSchedule(tripScheduleDocId: String) {
        Log.d("ScheduleViewModel", "deleteSchedule: $tripScheduleDocId")
        viewModelScope.launch {
            val work1 = async(Dispatchers.IO) {
                tripScheduleService.removeInvitedScheduleList(
                    application.loginUserModel.userDocId,
                    tripScheduleDocId
                )
            }.await()

            val work2 = async(Dispatchers.IO) {
                tripScheduleService.removeScheduleInviteList(
                    tripScheduleDocId,
                    application.loginUserModel.userDocId
                )
            }.await()
        }
    }



    // 해당 일정 상세 화면으로 이동
    fun moveToScheduleDetailScreen(scheduleModel: TripScheduleModel) {
        // scheduleCity와 일치하는 AreaCode 찾기 (없으면 0 반환)
        val areaCodeValue = AreaCode.entries.firstOrNull { it.areaName == scheduleModel.scheduleCity }?.areaCode ?: 0
        Log.d("ScheduleViewModel", "areaCodeValue: $areaCodeValue")

        application.navHostController.navigate(
            "${ScheduleScreenName.SCHEDULE_DETAIL_SCREEN.name}?" +
                    "tripScheduleDocId=${scheduleModel.tripScheduleDocId}&areaName=${scheduleModel.scheduleCity}&areaCode=$areaCodeValue"
        )
    }

}