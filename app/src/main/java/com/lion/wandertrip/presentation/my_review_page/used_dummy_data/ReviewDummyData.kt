package com.lion.wandertrip.presentation.my_review_page.used_dummy_data

import com.google.firebase.Timestamp
import com.lion.wandertrip.model.ReviewModel
import java.util.Calendar

class ReviewDummyData {
  companion object{
      val dummyDataList = mutableListOf<ReviewModel>(
          ReviewModel(
              reviewTitle = "사려니 숲길",
              reviewRatingScore = 0.0f,
              reviewContent = "리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@",
              reviewImageList = listOf(
                  "http://tong.visitkorea.or.kr/cms/resource/15/1974215_image2_1.jpg",
                  "http://tong.visitkorea.or.kr/cms/resource/83/2612483_image2_1.jpg",
              ),
              reviewTimeStamp = Timestamp(Calendar.getInstance().apply {
                  set(2025, 1, 16, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
              }.time),
              reviewState = 1 // 정상상태
          ),
          ReviewModel(
              reviewTitle = "사려니 숲길",
              reviewRatingScore = 0.2f,
              reviewContent = "리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@",
              reviewImageList = listOf(
                  "http://tong.visitkorea.or.kr/cms/resource/15/1974215_image2_1.jpg",
                  "http://tong.visitkorea.or.kr/cms/resource/83/2612483_image2_1.jpg",
              ),
              reviewTimeStamp = Timestamp(Calendar.getInstance().apply {
                  set(2025, 1, 16, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
              }.time),
              reviewState = 2 // 삭제됨 상태
          ),
          ReviewModel(
              reviewTitle = "첨성대",
              reviewRatingScore = 0.3f,
              reviewContent = "첨성대 입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@",
              reviewImageList = listOf(
                  "http://tong.visitkorea.or.kr/cms/resource/01/2656601_image2_1.jpg",
              ),
              reviewTimeStamp = Timestamp(Calendar.getInstance().apply {
                  set(2025, 1, 15, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
              }.time),
              reviewState = 1 // 정상상태
          ),
          ReviewModel(
              reviewTitle = "불국사",
              reviewRatingScore = 0.7f,
              reviewContent = "불국사입니다. 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@",
              reviewImageList = listOf(
                  "http://tong.visitkorea.or.kr/cms/resource/03/2678603_image2_1.jpg",
              ),
              reviewTimeStamp = Timestamp(Calendar.getInstance().apply {
                  set(2025, 1, 14, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
              }.time),
              reviewState = 1 // 정상상태
          ),
          ReviewModel(
              reviewTitle = "창덕궁",
              reviewRatingScore = 0.8f,
              reviewContent = "창덕궁. 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@",
              reviewImageList = listOf(
                  "http://tong.visitkorea.or.kr/cms/resource/03/3092503_image2_1.jpg",
              ),
              reviewTimeStamp = Timestamp(Calendar.getInstance().apply {
                  set(2025, 1, 13, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
              }.time),
              reviewState = 1 // 정상상태
          ),
          ReviewModel(
              reviewTitle = "경복궁",
              reviewRatingScore = 1.0f,
              reviewContent = "경복궁. 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@",
              reviewImageList = listOf(
                  "http://tong.visitkorea.or.kr/cms/resource/33/2678633_image2_1.jpg",
              ),
              reviewTimeStamp = Timestamp(Calendar.getInstance().apply {
                  set(2025, 1, 12, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
              }.time),
              reviewState = 1 // 정상상태
          )

      )
  }
}