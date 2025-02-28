package com.lion.wandertrip.presentation.schedule_city_select.city_roulette

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.lion.wandertrip.TripApplication
import com.lion.wandertrip.model.TripScheduleModel
import com.lion.wandertrip.service.TripScheduleService
import com.lion.wandertrip.util.AreaCode
import com.lion.wandertrip.util.BotNavScreenName
import com.lion.wandertrip.util.RouletteScreenName
import com.lion.wandertrip.util.ScheduleScreenName
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouletteCityViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    val tripScheduleService: TripScheduleService
) : ViewModel() {
    val application = context as TripApplication

    // 룰렛 항목
    var cities = mutableStateListOf<AreaCode>()

    // 일정 모델
    val tripScheduleModel = TripScheduleModel()

    // 룰렛의 도시 항목 추가 화면 으로 이동
    fun moveToRouletteCitySelectScreen() {
        application.navHostController.navigate(RouletteScreenName.ROULETTE_CITY_SELECT_SCREEN.name)
    }

    // 일정 상세 화면 으로 이동
    fun moveToScheduleDetailScreen(areaName: String) {
        // 도시 이름 으로 도시 코드 찾기
        val areaCode = AreaCode.entries.find { it.areaName == areaName }?.areaCode

        // 일정 상세 화면 이동
        application.navHostController.navigate(
            "${ScheduleScreenName.SCHEDULE_DETAIL_SCREEN.name}?" +
                    "tripScheduleDocId=${tripScheduleModel.tripScheduleDocId}&areaName=${areaName}&areaCode=${areaCode}",
        ) {
            popUpTo(BotNavScreenName.BOT_NAV_SCREEN_HOME.name) { inclusive = false }
            launchSingleTop = true
        }
    }

    // 일정 추가
    fun addTripSchedule(
        scheduleTitle: String,
        scheduleStartDate: Timestamp,
        scheduleEndDate: Timestamp,
        areaName: String,
    ) {
        val scheduleDateList = generateDateList(scheduleStartDate, scheduleEndDate)
        tripScheduleModel.userID = application.loginUserModel.userId
        tripScheduleModel.userNickName = application.loginUserModel.userNickName
        tripScheduleModel.scheduleCity = areaName
        tripScheduleModel.scheduleTitle = scheduleTitle
        tripScheduleModel.scheduleStartDate = scheduleStartDate
        tripScheduleModel.scheduleEndDate = scheduleEndDate
        tripScheduleModel.scheduleDateList = scheduleDateList
        tripScheduleModel.scheduleInviteList += application.loginUserModel.userDocId


        viewModelScope.launch {
            val work = async(Dispatchers.IO) {
                tripScheduleService.addTripSchedule(tripScheduleModel)
            }.await()
            tripScheduleModel.tripScheduleDocId = work

            val work2 = async(Dispatchers.IO) {
                tripScheduleService.addTripDocIdToUserScheduleList(
                    application.loginUserModel.userDocId,
                    tripScheduleModel.tripScheduleDocId
                )
            }.await()

            // 일정 상세 화면 으로 이동
            moveToScheduleDetailScreen(areaName)
        }
    }

    // 특정 기간 동안의 날짜 목록 생성 함수
    fun generateDateList(startDate: Timestamp, endDate: Timestamp): List<Timestamp> {
        val dateList = mutableListOf<Timestamp>()
        var currentTimestamp = startDate

        while (currentTimestamp.seconds <= endDate.seconds) {
            dateList.add(currentTimestamp)
            currentTimestamp = Timestamp(currentTimestamp.seconds + 86400, 0) // 하루(24시간 = 86400초) 추가
        }

        return dateList
    }

}