package com.lion.wandertrip.presentation.my_trip_note.used_dummy_data

import com.google.firebase.Timestamp
import com.lion.wandertrip.model.TripNoteModel
import java.util.Calendar

class TripNoteListDummyData {
    companion object{
        val tripNoteDummyData = mutableListOf<TripNoteModel>(
            TripNoteModel(
                userNickname = "닉네임1@@@@@@@@@@@닉네임1@@@@@@@@@@@",
                tripNoteTitle = "제목1@@@제목1@@@제목1@@@제목1@@@제목1@@@제목1@@@제목1@@@제목1@@@제목1@@@제목1@@@제목1@@@제목1@@@",
                tripNoteContent = "내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용",
                tripNoteImage = listOf("http://tong.visitkorea.or.kr/cms/resource/26/2015126_image2_1.jpg"),
                tripNoteScrapCount = 3,
                tripNoteTimeStamp = Timestamp(Calendar.getInstance().apply {
                    set(2025, 1, 16, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
                }.time)
            ),
            TripNoteModel(
                userNickname = "닉네임2",
                tripNoteTitle = "제목2",
                tripNoteContent = "내용내용2",
                tripNoteImage = listOf("http://tong.visitkorea.or.kr/cms/resource/26/2015126_image3_1.jpg", "http://tong.visitkorea.or.kr/cms/resource/85/2504485_image2_1.jpg"),
                tripNoteScrapCount = 5,
                tripNoteTimeStamp = Timestamp(Calendar.getInstance().apply {
                    set(2025, 1, 15, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
                }.time)



            ),
            TripNoteModel(
                userNickname = "닉네임3",
                tripNoteTitle = "제목3",
                tripNoteContent = "내용내용2",
                tripNoteImage = listOf("http://tong.visitkorea.or.kr/cms/resource/35/2706135_image2_1.jpg", "http://tong.visitkorea.or.kr/cms/resource/85/2504485_image2_1.jpg"),
                tripNoteScrapCount = 7,
                tripNoteTimeStamp = Timestamp(Calendar.getInstance().apply {
                    set(2025, 1, 14, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
                }.time)

            ),
            TripNoteModel(
                userNickname = "닉네임4",
                tripNoteTitle = "제목4",
                tripNoteContent = "내용내용2",
                tripNoteImage = listOf("http://tong.visitkorea.or.kr/cms/resource/35/2706135_image3_1.jpg", "http://tong.visitkorea.or.kr/cms/resource/85/2504485_image2_1.jpg"),
                tripNoteScrapCount = 9,
                tripNoteTimeStamp = Timestamp(Calendar.getInstance().apply {
                    set(2025, 1, 13, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
                }.time)
            ),
            TripNoteModel(
                userNickname = "닉네임4",
                tripNoteTitle = "제목4",
                tripNoteContent = "내용내용2",
                tripNoteImage = listOf("http://tong.visitkorea.or.kr/cms/resource/79/2672379_image2_1.bmp", "http://tong.visitkorea.or.kr/cms/resource/85/2504485_image2_1.jpg"),
                tripNoteScrapCount = 9,
                tripNoteTimeStamp = Timestamp(Calendar.getInstance().apply {
                    set(2025, 1, 12, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
                }.time)
            ),
            TripNoteModel(
                userNickname = "닉네임4",
                tripNoteTitle = "제목4",
                tripNoteContent = "내용내용2",
                tripNoteImage = listOf("http://tong.visitkorea.or.kr/cms/resource/26/3041326_image2_1.JPG", "http://tong.visitkorea.or.kr/cms/resource/85/2504485_image2_1.jpg"),
                tripNoteScrapCount = 9,
                tripNoteTimeStamp = Timestamp(Calendar.getInstance().apply {
                    set(2025, 1, 11, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
                }.time)
            ),
        )
    }

}