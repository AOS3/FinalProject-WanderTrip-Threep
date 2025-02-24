package com.lion.wandertrip.retrofit_for_practice


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class TripCommonItemsApiResponse(
    val response: ResponseWrapper
)

@Serializable
data class ResponseWrapper(
    val header: Header,
    val body: Body
)

@Serializable
data class Header(
    val resultCode: String,
    val resultMsg: String
)

@Serializable
data class Body(
    val items: ItemsWrapper
)
@Serializable
data class ItemsWrapper(
    val item: List<Item>
)

@Serializable
data class Item(
    @SerializedName("contentid") val contentId: String?,
    @SerializedName("contenttypeid") val contentTypeId: String?,
    val title: String?,
    val tel: String?,
    val homepage: String?,
    @SerializedName("firstimage") val firstImage: String?,
    val areaCode: String?,
    @SerializedName("sigungucode") val siGunGuCode: String?,
    val addr1: String?,
    val addr2: String?,
    @SerializedName("mapx") val mapLat: String?,
    @SerializedName("mapy") val mapLng: String?,
    val overview: String?
)