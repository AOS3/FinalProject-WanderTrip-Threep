package com.lion.wandertrip.retrofit

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://apis.data.go.kr/B551011/KorService1/"
    var gson= GsonBuilder().setLenient().create()
    private val json = Json {
        ignoreUnknownKeys = true // ✅ 응답에 예상치 못한 키가 있어도 무시
        isLenient = true // ✅ JSON 형식이 약간 틀려도 허용
        encodeDefaults = true // ✅ 기본값을 자동으로 직렬화
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit by lazy {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(json.asConverterFactory(contentType)) // ✅ 오류 해결
            .client(OkHttpClient.Builder().build())
            .build()
    }

    val apiService: PublicDataApiService by lazy {
        retrofit.create(PublicDataApiService::class.java)
    }
}
