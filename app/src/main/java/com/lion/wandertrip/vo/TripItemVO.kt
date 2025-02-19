package com.lion.wandertrip.vo

import com.lion.wandertrip.model.Item
import com.lion.wandertrip.model.TripItemModel

class TripItemVO {
    var addr1: String = ""
    var addr2: String = ""
    var areaCode: String = ""
    var contentId: String = ""
    var contentTypeId: String = ""
    var firstImage: String = ""
    var mapLat: Double = 0.0
    var mapLong: Double = 0.0
    var tel: String = ""
    var title: String = ""

    fun toTripItemModel(): TripItemModel {
        val tripItemModel = TripItemModel()
        tripItemModel.addr1 = addr1
        tripItemModel.addr2 = addr2
        tripItemModel.areaCode = areaCode
        tripItemModel.contentId = contentId
        tripItemModel.contentTypeId = contentTypeId
        tripItemModel.firstImage = firstImage
        tripItemModel.mapLat = mapLat
        tripItemModel.mapLong = mapLong
        tripItemModel.tel = tel
        tripItemModel.title = title
        return tripItemModel
    }

    // ✅ API Item → TripItemVO 변환 함수 추가
    companion object {
        fun from(item: Item): TripItemVO {
            return TripItemVO().apply {
                addr1 = item.addr1 ?: ""
                addr2 = item.addr2 ?: ""
                areaCode = item.areacode ?: ""
                contentId = item.contentid ?: ""
                contentTypeId = item.contenttypeid ?: ""
                firstImage = item.firstimage ?: ""
                mapLat = item.mapy?.toDoubleOrNull() ?: 0.0
                mapLong = item.mapx?.toDoubleOrNull() ?: 0.0
                tel = item.tel ?: ""
                title = item.title ?: ""
            }
        }
    }
}
