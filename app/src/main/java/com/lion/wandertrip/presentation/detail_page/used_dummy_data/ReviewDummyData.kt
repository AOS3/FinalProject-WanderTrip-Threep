package com.lion.wandertrip.presentation.detail_page.used_dummy_data

import com.google.firebase.Timestamp
import com.lion.wandertrip.model.ReviewModel
import com.lion.wandertrip.presentation.my_review_page.used_dummy_data.ReviewDummyData
import java.util.Calendar

class ReviewDummyData {
    companion object {
        val detailReviewDataList = mutableListOf<ReviewModel>(
            ReviewModel(
                reviewTitle = "사려니 숲길",
                reviewRatingScore = 1.5f,
                reviewContent = "리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@",
                reviewImageList = listOf(
                    "http://tong.visitkorea.or.kr/cms/resource/15/1974215_image2_1.jpg",
                    "http://tong.visitkorea.or.kr/cms/resource/83/2612483_image2_1.jpg",
                ),
                reviewTimeStamp = Timestamp(Calendar.getInstance().apply {
                    set(2030, 1, 16, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
                }.time),
                reviewState = 1
            ),
            ReviewModel(
                reviewTitle = "사려니 숲길",
                reviewRatingScore = 4.2f,
                reviewContent = "리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@",
                reviewImageList = listOf(
                    "http://tong.visitkorea.or.kr/cms/resource/15/1974215_image2_1.jpg",
                    "http://tong.visitkorea.or.kr/cms/resource/83/2612483_image2_1.jpg",
                ),
                reviewTimeStamp = Timestamp(Calendar.getInstance().apply {
                    set(2000, 1, 16, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
                }.time),
                reviewState = 1
            ),
            ReviewModel(
                reviewTitle = "사려니 숲길",
                reviewRatingScore = 5.0f,
                reviewContent = "리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@",
                reviewImageList = listOf(
                    "http://tong.visitkorea.or.kr/cms/resource/15/1974215_image2_1.jpg",
                    "http://tong.visitkorea.or.kr/cms/resource/83/2612483_image2_1.jpg",
                ),
                reviewTimeStamp = Timestamp(Calendar.getInstance().apply {
                    set(2000, 1, 16, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
                }.time),
                reviewState = 1
            ),
            ReviewModel(
                reviewTitle = "사려니 숲길",
                reviewRatingScore = 1.0f,
                reviewContent = "리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@리뷰 내용입니다@@",
                reviewImageList = listOf(
                    "http://tong.visitkorea.or.kr/cms/resource/15/1974215_image2_1.jpg",
                    "http://tong.visitkorea.or.kr/cms/resource/83/2612483_image2_1.jpg",
                ),
                reviewTimeStamp = Timestamp(Calendar.getInstance().apply {
                    set(2030, 1, 16, 0, 0, 0) // 2025년 3월 14일 (월은 0부터 시작하므로 2 = 3월)
                }.time),
                reviewState = 1
            ),
        )
    }
}