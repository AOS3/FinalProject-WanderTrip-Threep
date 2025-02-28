package com.lion.wandertrip.presentation.bottom.my_info_page.used_dummy_data

import com.google.firebase.Timestamp
import com.lion.wandertrip.model.TripScheduleModel
import java.util.Calendar

class ScheduleDummyData {
  companion object{

      val scheduleStartDate1 = Timestamp(Calendar.getInstance().apply {
          set(2025, 1, 14, 11, 30, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
      }.time)

      val scheduleStartDate2 = Timestamp(Calendar.getInstance().apply {
          set(2025, 1, 14, 12, 30, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
      }.time)

      val scheduleStartDate3 = Timestamp(Calendar.getInstance().apply {
          set(2025, 4, 14, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
      }.time)

      val scheduleStartDate4 = Timestamp(Calendar.getInstance().apply {
          set(2025, 5, 14, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
      }.time)

      val scheduleDummyDataList = mutableListOf<TripScheduleModel>(
          TripScheduleModel(
              scheduleCity = "제주",
              scheduleStartDate = scheduleStartDate1
          ),
          TripScheduleModel(
              scheduleCity = "서울",
              scheduleStartDate = scheduleStartDate2
          ),
          TripScheduleModel(
              scheduleCity = "경주",
              scheduleStartDate = scheduleStartDate3
          ),
          TripScheduleModel(
              scheduleCity = "부산",
              scheduleStartDate = scheduleStartDate4
          )
      )




  }
}