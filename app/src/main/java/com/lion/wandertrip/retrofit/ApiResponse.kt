package com.lion.wandertrip.retrofit

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val response: ResponseWrapper
)

@Serializable
data class ResponseWrapper(
    val header: Header,
    val body: Body?
)

@Serializable
data class Header(
    val resultCode: String,
    val resultMsg: String
)

@Serializable
data class Body(
    val items: Items?,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)

@Serializable
data class Items(
    val item: List<Item>?
)

@Serializable
data class Item(
    val addr1: String? = null,
    val addr2: String? = null,
    val areacode: String? = null,
    val booktour: String? = null,
    val cat1: String? = null,
    val cat2: String? = null,
    val cat3: String? = null,
    val contentid: String? = null,
    val contenttypeid: String? = null,
    val createdtime: String? = null,
    val firstimage: String? = null,
    val firstimage2: String? = null,
    val cpyrhtDivCd: String? = null,
    val mapx: String? = null,
    val mapy: String? = null,
    val mlevel: String? = null,
    val modifiedtime: String? = null,
    val sigungucode: String? = null,
    val tel: String? = null,
    val title: String? = null,
    val zipcode: String? = null,
    val showflag: String? = null
)
