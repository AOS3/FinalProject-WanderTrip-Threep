package com.lion.wandertrip.vo

import com.google.firebase.Timestamp
import com.lion.wandertrip.model.ContentsModel
import com.lion.wandertrip.model.ReviewModel

class ContentsVO {
    var contentDocId: String = ""
    var contentId: String = ""
    var ratingScore: Float = 0.0f
    var reviewList: List<ReviewVO> = emptyList()
    var getRatingCount : Int = 0
    var interestingCount : Int = 0

    fun toDetailReviewModel(): ContentsModel {
        val detailReviewModel = ContentsModel()
        detailReviewModel.contentDocId = contentDocId
        detailReviewModel.contentId = contentId
        detailReviewModel.ratingScore = ratingScore
        detailReviewModel.reviewList = reviewList.map { it.toReviewItemModel() }
        detailReviewModel.getRatingCount =getRatingCount
        detailReviewModel.interestingCount =interestingCount
        return detailReviewModel
    }
}
