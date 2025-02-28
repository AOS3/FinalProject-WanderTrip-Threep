package com.lion.wandertrip.presentation.my_trip_page.used_dummy_data

import com.google.firebase.Timestamp
import com.lion.wandertrip.model.TripScheduleModel
import java.util.Calendar

class ComeAndPastScheduleDummyData {
    companion object{

        val scheduleStartDate1 = Timestamp(Calendar.getInstance().apply {
            set(2026, 1, 14, 11, 30, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val scheduleStartDate2 = Timestamp(Calendar.getInstance().apply {
            set(2026, 1, 14, 12, 30, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val scheduleStartDate3 = Timestamp(Calendar.getInstance().apply {
            set(2026, 4, 14, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val scheduleStartDate4 = Timestamp(Calendar.getInstance().apply {
            set(2026, 5, 14, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val scheduleStartDate5 = Timestamp(Calendar.getInstance().apply {
            set(2026, 5, 14, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val scheduleStartDate6 = Timestamp(Calendar.getInstance().apply {
            set(2026, 5, 14, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val scheduleStartDate7 = Timestamp(Calendar.getInstance().apply {
            set(2025, 1, 15, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val scheduleStartDate8 = Timestamp(Calendar.getInstance().apply {
            set(2025, 1, 16, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val scheduleStartDate9 = Timestamp(Calendar.getInstance().apply {
            set(2025, 1, 17, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val scheduleStartDate10 = Timestamp(Calendar.getInstance().apply {
            set(2025, 1, 14, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val scheduleStartDate11 = Timestamp(Calendar.getInstance().apply {
            set(2025, 1, 13, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val scheduleStartDate12 = Timestamp(Calendar.getInstance().apply {
            set(2024, 5, 14, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val scheduleStartDate13 = Timestamp(Calendar.getInstance().apply {
            set(2024, 5, 12, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val scheduleStartDate14 = Timestamp(Calendar.getInstance().apply {
            set(2024, 5, 18, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val scheduleStartDate15 = Timestamp(Calendar.getInstance().apply {
            set(2024, 5, 14, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val scheduleStartDate16 = Timestamp(Calendar.getInstance().apply {
            set(2024, 8, 12, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val scheduleStartDate17 = Timestamp(Calendar.getInstance().apply {
            set(2024, 4, 25, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val scheduleStartDate18 = Timestamp(Calendar.getInstance().apply {
            set(2024, 6, 18, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val scheduleStartDate19 = Timestamp(Calendar.getInstance().apply {
            set(2024, 0, 1, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val scheduleStartDate20 = Timestamp(Calendar.getInstance().apply {
            set(2024, 11, 31, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val scheduleDummyDataList = mutableListOf<TripScheduleModel>(
            TripScheduleModel(
                scheduleCity = "제주",
                scheduleStartDate = scheduleStartDate1,
                scheduleEndDate = Timestamp(scheduleStartDate1.seconds + 86400, 0)
            ),
            TripScheduleModel(
                scheduleCity = "서울",
                scheduleStartDate = scheduleStartDate2,
                scheduleEndDate = Timestamp(scheduleStartDate2.seconds + 86400, 0)

            ),
            TripScheduleModel(
                scheduleCity = "경주",
                scheduleStartDate = scheduleStartDate3,
                scheduleEndDate = Timestamp(scheduleStartDate3.seconds + 86400, 0)

            ),
            TripScheduleModel(
                scheduleCity = "부산",
                scheduleStartDate = scheduleStartDate4,
                scheduleEndDate = Timestamp(scheduleStartDate4.seconds + 86400, 0)
            ),
            TripScheduleModel(
                scheduleCity = "양주",
                scheduleStartDate = scheduleStartDate5,
                scheduleEndDate = Timestamp(scheduleStartDate5.seconds + 86400, 0)
            ),
            TripScheduleModel(
                scheduleCity = "가평",
                scheduleStartDate = scheduleStartDate6,
                scheduleEndDate = Timestamp(scheduleStartDate6.seconds + 86400, 0)
            ),
            TripScheduleModel(
                scheduleCity = "양평",
                scheduleStartDate = scheduleStartDate7,
                scheduleEndDate = Timestamp(scheduleStartDate7.seconds + 86400, 0)
            ),
            TripScheduleModel(
                scheduleCity = "속초",
                scheduleStartDate = scheduleStartDate8,
                scheduleEndDate = Timestamp(scheduleStartDate8.seconds + 86400, 0)
            ),
            TripScheduleModel(
                scheduleCity = "경주",
                scheduleStartDate = scheduleStartDate9,
                scheduleEndDate = Timestamp(scheduleStartDate9.seconds + 86400, 0)
            ),
            TripScheduleModel(
                scheduleCity = "강릉",
                scheduleStartDate = scheduleStartDate10,
                scheduleEndDate = Timestamp(scheduleStartDate10.seconds + 86400, 0)
            ),
            TripScheduleModel(
                scheduleCity = "서울",
                scheduleStartDate = scheduleStartDate11,
                scheduleEndDate = Timestamp(scheduleStartDate11.seconds + 86400, 0)
            ),
            TripScheduleModel(
                scheduleCity = "전주",
                scheduleStartDate = scheduleStartDate12,
                scheduleEndDate = Timestamp(scheduleStartDate12.seconds + 86400, 0)
            ),
            TripScheduleModel(
                scheduleCity = "울산",
                scheduleStartDate = scheduleStartDate13,
                scheduleEndDate = Timestamp(scheduleStartDate13.seconds + 86400, 0)
            ),
            TripScheduleModel(
                scheduleCity = "하남",
                scheduleStartDate = scheduleStartDate14,
                scheduleEndDate = Timestamp(scheduleStartDate14.seconds + 86400, 0)
            ),
            TripScheduleModel(
                scheduleCity = "포항",
                scheduleStartDate = scheduleStartDate15,
                scheduleEndDate = Timestamp(scheduleStartDate15.seconds + 86400, 0)
            ),
            TripScheduleModel(
                scheduleCity = "연천",
                scheduleStartDate = scheduleStartDate16,
                scheduleEndDate = Timestamp(scheduleStartDate16.seconds + 86400, 0)
            ),
            TripScheduleModel(
                scheduleCity = "춘천",
                scheduleStartDate = scheduleStartDate17,
                scheduleEndDate = Timestamp(scheduleStartDate17.seconds + 86400, 0)
            ),
            TripScheduleModel(
                scheduleCity = "이천",
                scheduleStartDate = scheduleStartDate18,
                scheduleEndDate = Timestamp(scheduleStartDate18.seconds + 86400, 0)
            ),
            TripScheduleModel(
                scheduleCity = "인천",
                scheduleStartDate = scheduleStartDate19,
                scheduleEndDate = Timestamp(scheduleStartDate19.seconds + 86400, 0)
            ),
            TripScheduleModel(
                scheduleCity = "대구",
                scheduleStartDate = scheduleStartDate20,
                scheduleEndDate = Timestamp(scheduleStartDate20.seconds + 86400, 0)
            )
        )




    }
}