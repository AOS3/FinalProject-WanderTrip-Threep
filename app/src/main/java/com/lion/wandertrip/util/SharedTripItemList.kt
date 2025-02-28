package com.lion.wandertrip.util

import androidx.compose.runtime.mutableStateListOf
import com.lion.wandertrip.model.TripItemModel

class SharedTripItemList {

    companion object{
        
        // 공공데이터 포털에서 받아온 여행지 목록 리스트 ( 관광지 추가, 음식점 추가, 숙소 추가 와 룰렛 화면에서 쓰임 )
        val sharedTripItemList = mutableStateListOf<TripItemModel>()

        // ✅ 선택된 여행지 목록 (룰렛 항목)
        val rouletteItemList = mutableStateListOf<TripItemModel>()

    }

}