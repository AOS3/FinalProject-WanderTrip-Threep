package com.lion.wandertrip.model

import com.lion.wandertrip.vo.ContentsVO

class ContentsModel(
    var contentDocId: String = "",   // 컨텐츠 문서 ID (컬렉션)
    var contentId: String = "",      // 컨텐츠 ID
    var ratingScore: Float = 0.0f,    // 평균 별점
    var reviewList: List<ReviewModel> = emptyList(), // 리뷰 리스트 (서브 컬렉션)
    var getRatingCount : Int = 0,
    var interestingCount : Int = 0,
) {
    fun toDetailReviewVO(): ContentsVO {
        val detailReviewVO = ContentsVO()
        detailReviewVO.contentDocId = contentDocId
        detailReviewVO.contentId = contentId
        detailReviewVO.ratingScore = ratingScore
        detailReviewVO.reviewList = reviewList.map { it.toReviewItemVO() }
        detailReviewVO.getRatingCount=getRatingCount
        detailReviewVO.interestingCount=interestingCount

        return detailReviewVO
    }
}
