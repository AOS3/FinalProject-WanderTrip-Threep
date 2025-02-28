package com.lion.wandertrip.model

data class UserInterestingModel(
    val contentID: String = "",
    val contentTypeID: String = "",
    val contentTitle: String = "",
    val areacode: String = "",
    val sigungucode: String = "",
    var saveCount: Int = 0,
    var starRatingCount: Int = 0,
    var ratingScore : Float = 0.0f,
    val smallImagePath: String = "",
    val cat2 : String = "",
    val cat3 : String = "",
    val addr1 : String = "",
    val addr2 : String = "",
)