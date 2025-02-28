package com.lion.wandertrip.vo

import com.google.firebase.Timestamp
import com.lion.wandertrip.model.RecentTripItemModel
import com.lion.wandertrip.util.ContentTypeId

data class RecentTripItemVO(
    val contentID :String = "",
    val contentTypeID: String = "",
    val title : String = "",
    val imageUri : String ="",
    val clickedTimeStamp : Timestamp = Timestamp.now()
) {
    fun toRecentTripItemModel(recentTripItemVO: RecentTripItemVO): RecentTripItemModel {
        return RecentTripItemModel(
            contentID = recentTripItemVO.contentID,
            contentTypeID = recentTripItemVO.contentTypeID,
            title = recentTripItemVO.title,
            imageUri = recentTripItemVO.imageUri,
            clickedTimeStamp = recentTripItemVO.clickedTimeStamp
        )
    }

}