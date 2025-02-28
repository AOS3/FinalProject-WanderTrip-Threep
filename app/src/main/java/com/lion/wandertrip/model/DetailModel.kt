package com.lion.wandertrip.model

import com.lion.wandertrip.vo.DetailVO

class DetailModel(
    var contentID : String = "",            // contentID 추가, 저장하기, 리뷰 남길때 사용해야함
    var detailTitle: String = "",           // 상세페이지 제목
    var detailRating: Float = 0.0f,         // 상세페이지 별점 float 타입으로 변경
    var detailLat: Double = 0.0,            // 상세페이지 위도(Y)
    var detailLong: Double = 0.0,           // 상세페이지 경도(X)
    var detailImage: String = "",           // 상세페이지 이미지
    var detailDescription: String = "",     // 상세페이지 설명
    var detailAddress: String = "",         // 상세페이지 주소
    var detailPhoneNumber: String = "",     // 상세페이지 전화번호 (0으로 시작하는 문제 방지)
    var detailHomepage: String = "" ,       // 상세페이지 홈페이지
    var likeCnt : Int = 0,
    var ratingCount : Int = 0,
) {
    fun toDetailVO(): DetailVO {
        val detailVO = DetailVO()
        detailVO.contentID = contentID
        detailVO.detailTitle = detailTitle
        detailVO.detailRating = detailRating
        detailVO.detailLat = detailLat
        detailVO.detailLong = detailLong
        detailVO.detailImage = detailImage
        detailVO.detailDescription = detailDescription
        detailVO.detailAddress = detailAddress
        detailVO.detailPhoneNumber = detailPhoneNumber
        detailVO.detailHomepage = detailHomepage
        detailVO.likeCnt=likeCnt
        detailVO.ratingCount=ratingCount

        return detailVO
    }
}
