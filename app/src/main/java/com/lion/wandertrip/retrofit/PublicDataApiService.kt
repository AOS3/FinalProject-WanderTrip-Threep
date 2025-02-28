package com.lion.wandertrip.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

interface PublicDataApiService {
    @GET("areaBasedSyncList1")
    suspend fun getItems(
        @Query("serviceKey") serviceKey: String,       // 인증키
        @Query("numOfRows") numOfRows: Int,           // 한 페이지 결과 수
        @Query("pageNo") pageNo: Int,                 // 페이지 번호
        @Query("MobileOS") mobileOS: String,          // OS 구분 (예: AND)
        @Query("MobileApp") mobileApp: String,        // 어플명
        @Query("_type") type: String,                 // 응답 형식 (JSON)
        @Query("showflag") showflag: String,          // 컨텐츠 표출 여부 (1=표출)
        @Query("listYN") listYN: String,              // 목록 구분 (Y=목록, N=개수)
        @Query("arrange") arrange: String,            // 정렬 기준 (A=제목순)
        @Query("contentTypeId") contentTypeId: String,// 관광타입 ID (12=관광지)
        @Query("areaCode") areaCode: String           // 지역 코드
    ): String
}