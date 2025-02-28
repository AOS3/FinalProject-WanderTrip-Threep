package com.lion.wandertrip.presentation.detail_page.used_dummy_data

import com.google.firebase.Timestamp
import com.lion.wandertrip.model.ScheduleItem
import com.lion.wandertrip.model.TripScheduleModel
import java.util.Calendar

class TripScheduleDummyData {
    companion object {

        val date1_1 = Timestamp(Calendar.getInstance().apply {
            set(2025, 1, 20, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val date1_2 = Timestamp(Calendar.getInstance().apply {
            set(2025, 1, 22, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val date1_3 = Timestamp(Calendar.getInstance().apply {
            set(2025, 1, 23, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val dateList1 = listOf(
            date1_1, date1_2, date1_3
        )
        val item1_1 = ScheduleItem(
            "", // 문서 ID
            date1_1,// 일정 날짜
            0,// 순서값
            "부산역",// 아이템 이름
            "관광지"// 목적지 분류 (관광지, 음식점, 숙소 중)
        )
        val item1_2 = ScheduleItem(
            "", // 문서 ID
            date1_1,// 일정 날짜
            1,// 순서값
            "서면역",// 아이템 이름
            "관광지"// 목적지 분류 (관광지, 음식점, 숙소 중)
        )

        val model1 = TripScheduleModel(
            tripScheduleDocId = "",// 문서 ID
            userID = "",// 유저 ID
            userNickName = "testNickName",// 유저 닉네임
            scheduleTitle = "test1Title",// 일정 제목
            scheduleStartDate = date1_1,// 일정 시작 날짜
            scheduleCity = "해운대",// 일정 도시
            scheduleEndDate = date1_3,// 일정 끝 날짜
            scheduleInviteList = emptyList(),// 함께하는 유저 문서 ID 리스트
            scheduleDateList = dateList1,
            scheduleTimeStamp = Timestamp.now(),// 데이터가 들어온 시간
            scheduleState = 1,// 일정 상태 (1: 활성화, 2: 비활성화)
            scheduleItems = listOf(item1_1, item1_2)
        )
// -------------------------------------------------------------------

        val date2_1 = Timestamp(Calendar.getInstance().apply {
            set(2025, 2, 20, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val date2_2 = Timestamp(Calendar.getInstance().apply {
            set(2025, 2, 22, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val date2_3 = Timestamp(Calendar.getInstance().apply {
            set(2025, 2, 23, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val dateList2 = listOf(
            date2_1, date2_2, date2_3
        )
        val item2_1 = ScheduleItem(
            "", // 문서 ID
            date2_1,// 일정 날짜
            0,// 순서값
            "광안대교",// 아이템 이름
            "관광지"// 목적지 분류 (관광지, 음식점, 숙소 중)
        )
        val item2_2 = ScheduleItem(
            "", // 문서 ID
            date2_1,// 일정 날짜
            1,// 순서값
            "해운대",// 아이템 이름
            "관광지"// 목적지 분류 (관광지, 음식점, 숙소 중)
        )

        val model2 = TripScheduleModel(
            tripScheduleDocId = "",// 문서 ID
            userID = "",// 유저 ID
            userNickName = "testNickName",// 유저 닉네임
            scheduleTitle = "test1Title",// 일정 제목
            scheduleStartDate = date2_1,// 일정 시작 날짜
            scheduleCity = "광운대",// 일정 도시
            scheduleEndDate = date2_3,// 일정 끝 날짜
            scheduleInviteList = emptyList(),// 함께하는 유저 문서 ID 리스트
            scheduleDateList = dateList2,
            scheduleTimeStamp = Timestamp.now(),// 데이터가 들어온 시간
            scheduleState = 1,// 일정 상태 (1: 활성화, 2: 비활성화)
            scheduleItems = listOf(item2_1, item2_2)
        )

     // ------------------------------------------------------------------------------------------


        val date3_1 = Timestamp(Calendar.getInstance().apply {
            set(2025, 5, 20, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val date3_2 = Timestamp(Calendar.getInstance().apply {
            set(2025, 5, 21, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val date3_3 = Timestamp(Calendar.getInstance().apply {
            set(2025, 5, 22, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val date3_4 = Timestamp(Calendar.getInstance().apply {
            set(2025, 5, 23, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
        }.time)

        val dateList3 = listOf(
            date3_1, date3_2, date3_3,date3_4
        )
        val item3_1 = ScheduleItem(
            "", // 문서 ID
            date3_1,// 일정 날짜
            0,// 순서값
            "광안대교",// 아이템 이름
            "관광지"// 목적지 분류 (관광지, 음식점, 숙소 중)
        )

        val model3 = TripScheduleModel(
            tripScheduleDocId = "",// 문서 ID
            userID = "",// 유저 ID
            userNickName = "testNickName",// 유저 닉네임
            scheduleTitle = "test1Title",// 일정 제목
            scheduleStartDate = date3_1,// 일정 시작 날짜
            scheduleCity = "광운대",// 일정 도시
            scheduleEndDate = date3_4,// 일정 끝 날짜
            scheduleInviteList = emptyList(),// 함께하는 유저 문서 ID 리스트
            scheduleDateList = dateList3,
            scheduleTimeStamp = Timestamp.now(),// 데이터가 들어온 시간
            scheduleState = 1,// 일정 상태 (1: 활성화, 2: 비활성화)
            scheduleItems = listOf(item3_1)
        )



        val detailPageTripScheduleDummyData = mutableListOf<TripScheduleModel>(
            model1, model2,model3
        )

    }
}