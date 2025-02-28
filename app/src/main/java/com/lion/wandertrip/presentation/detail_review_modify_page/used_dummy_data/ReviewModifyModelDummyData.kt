package com.lion.wandertrip.presentation.detail_review_modify_page.used_dummy_data

import com.google.firebase.Timestamp
import com.lion.wandertrip.model.ReviewModel

class ReviewModifyModelDummyData {
    companion object{
        val imageList = mutableListOf(
            "http://tong.visitkorea.or.kr/cms/resource/15/1974215_image2_1.jpg",
            "http://tong.visitkorea.or.kr/cms/resource/83/2612483_image2_1.jpg"
        )
        val reviewModifyModel = ReviewModel(
            "", "", "닉네임123", "내용123", Timestamp.now(), 3.5f, imageList, "리뷰 제목", 1,
        )
    }
}