package com.lion.wandertrip.presentation.schedule_city_select.city_roulette.roulette_city_select

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.lion.wandertrip.TripApplication
import com.lion.wandertrip.util.AreaCode
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class RouletteCitySelectViewModel @Inject constructor(
    @ApplicationContext val context: Context,
) : ViewModel() {

    val application = context as TripApplication

    // 모든 도시 리스트
    var allCities by mutableStateOf(emptyList<AreaCode>())

    // 검색 결과 리스트
    var filteredCities by mutableStateOf(emptyList<AreaCode>())

    // 선택된 도시 리스트
    var selectedCities = mutableStateListOf<AreaCode>()

    init {
        addCity()
    }

    // 모든 도시 목록을 추가하는 함수
    fun addCity() {
        allCities = AreaCode.entries.toList() // ✅ 모든 도시 추가
        filteredCities = allCities // ✅ 초기에는 모든 도시 표시
    }

    // 검색어에 맞는 도시 필터링 함수
    fun updateFilteredCities(query: String) {
        filteredCities = if (query.isEmpty()) allCities
        else allCities.filter { it.areaName.contains(query, ignoreCase = true) }
    }

    // 선택된 도시 추가
    fun addSelectedCity(city: AreaCode) {
        if (!selectedCities.contains(city)) selectedCities.add(city)
    }

    // 선택된 도시 제거
    fun removeSelectedCity(city: AreaCode) {
        selectedCities.remove(city)
    }

    // 선택된 도시 리스트 업데이트 (외부 ViewModel과 데이터 공유)
    fun updateSelectedCities(newCities: List<AreaCode>) {
        selectedCities.clear()
        selectedCities.addAll(newCities)
    }
}
