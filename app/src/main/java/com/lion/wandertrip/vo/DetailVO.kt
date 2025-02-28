package com.lion.wandertrip.vo

import com.lion.wandertrip.model.DetailModel

class DetailVO {
    var detailTitle: String = ""
    var detailRating: Float = 0.0f
    var detailLat: Double = 0.0
    var detailLong: Double = 0.0
    var detailImage: String = ""
    var detailDescription: String = ""
    var detailAddress: String = ""
    var detailPhoneNumber: String = ""
    var detailHomepage: String = ""
    var contentID: String = ""  // contentID 추가, 저장하기, 리뷰 남길때 사용해야함
    var likeCnt : Int = 0
    var ratingCount : Int = 0

    fun toDetailModel(): DetailModel {
        val detailModel = DetailModel()
        detailModel.contentID = contentID
        detailModel.detailTitle = detailTitle
        detailModel.detailRating = detailRating
        detailModel.detailLat = detailLat
        detailModel.detailLong = detailLong
        detailModel.detailImage = detailImage
        detailModel.detailDescription = detailDescription
        detailModel.detailAddress = detailAddress
        detailModel.detailPhoneNumber = detailPhoneNumber
        detailModel.detailHomepage = detailHomepage
        detailModel.likeCnt = likeCnt
        detailModel.ratingCount = ratingCount
        return detailModel
    }
}
