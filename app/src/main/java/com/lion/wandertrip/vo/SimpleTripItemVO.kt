package com.lion.wandertrip.vo

import com.lion.wandertrip.model.SimpleTripItemModel
import com.lion.wandertrip.util.ContentTypeId

data class SimpleTripItemVO (
    val contentID: String ="",
    val contentTypeID: Int = ContentTypeId.TOURIST_ATTRACTION.contentTypeCode,
    val contentSmallImageUri: String="",
    val title: String="",
    val areaCode :String ="0",
    val siGunGuCode : String="0",
    val cat2 : String = "",
    val cat3 : String = "",
){
    fun toSimpleTripItemModel(simpleTripItemVo: SimpleTripItemVO): SimpleTripItemModel {
        val simpleTripItemModel = SimpleTripItemModel(
            contentID = simpleTripItemVo.contentID,
            contentTypeID = when (simpleTripItemVo.contentTypeID) {
                ContentTypeId.TOURIST_ATTRACTION.contentTypeCode -> ContentTypeId.TOURIST_ATTRACTION
                ContentTypeId.RESTAURANT.contentTypeCode -> ContentTypeId.RESTAURANT
                else -> ContentTypeId.ACCOMMODATION
            },
            contentSmallImageUri = simpleTripItemVo.contentSmallImageUri,
            title = simpleTripItemVo.title,
            areaCode = simpleTripItemVo.areaCode,
            siGunGuCode = simpleTripItemVo.siGunGuCode,
            cat2 = simpleTripItemVo.cat2,
            cat3 = simpleTripItemVo.cat3
        )

        return simpleTripItemModel
    }
}