package com.lion.wandertrip.model

import com.lion.wandertrip.util.ContentTypeId
import com.lion.wandertrip.vo.SimpleTripItemVO

data class SimpleTripItemModel(
    val contentID: String = "",
    val contentTypeID: ContentTypeId = ContentTypeId.TOURIST_ATTRACTION,
    val contentSmallImageUri: String = "",
    val title: String = "",
    val areaCode: String = "0",
    val siGunGuCode: String = "0",
    val cat2: String = "",
    val cat3: String = "",
) {
    fun toSimpleTripItemVO(simpleTripItemModel: SimpleTripItemModel): SimpleTripItemVO {
        val simpleTripItemVO = SimpleTripItemVO(
            contentID = simpleTripItemModel.contentID,
            contentTypeID = when (simpleTripItemModel.contentTypeID) {
                ContentTypeId.TOURIST_ATTRACTION -> ContentTypeId.TOURIST_ATTRACTION.contentTypeCode
                ContentTypeId.RESTAURANT -> ContentTypeId.RESTAURANT.contentTypeCode
                ContentTypeId.ACCOMMODATION -> ContentTypeId.ACCOMMODATION.contentTypeCode
            },
            contentSmallImageUri = simpleTripItemModel.contentSmallImageUri,
            title = simpleTripItemModel.title,
            areaCode = simpleTripItemModel.areaCode,
            siGunGuCode = simpleTripItemModel.siGunGuCode,
            cat2 = simpleTripItemModel.cat2,
            cat3 = simpleTripItemModel.cat3
        )

        return simpleTripItemVO
    }
}