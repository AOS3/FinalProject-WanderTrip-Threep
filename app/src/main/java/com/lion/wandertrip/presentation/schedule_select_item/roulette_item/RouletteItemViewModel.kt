package com.lion.wandertrip.presentation.schedule_select_item.roulette_item

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.lion.wandertrip.TripApplication
import com.lion.wandertrip.model.ScheduleItem
import com.lion.wandertrip.model.TripItemModel
import com.lion.wandertrip.presentation.schedule_select_item.ScheduleSelectItemViewModel
import com.lion.wandertrip.service.TripScheduleService
import com.lion.wandertrip.util.ContentTypeId
import com.lion.wandertrip.util.RouletteScreenName
import com.lion.wandertrip.util.ScheduleScreenName
import com.lion.wandertrip.util.SharedTripItemList
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouletteItemViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    val tripScheduleService: TripScheduleService
) : ViewModel() {

    val application = context as TripApplication

    // ✅ 전체 여행지 목록
    val tripItemList = mutableStateListOf<TripItemModel>()

    // ✅ 선택된 여행지 목록 (룰렛 항목)
    val rouletteItemList = mutableStateListOf<TripItemModel>()

    // 여행지 선택 화면으로 이동
    fun moveToRouletteItemSelectScreen() {
        application.navHostController.navigate(RouletteScreenName.ROULETTE_ITEM_SELECT_SCREEN.name)
    }

    // 일정에 여행지 항목 추가
    fun addTripItemToSchedule(
        tripItemModel: TripItemModel,
        tripScheduleDocId: String,
        areaName: String,
        areaCode: Int,
        scheduleDate: Timestamp,
    ) {

        viewModelScope.launch {
            val work1 = async(Dispatchers.IO) {
                val scheduleItem = ScheduleItem(
                    itemTitle = tripItemModel.title,
                    itemType = when(tripItemModel.contentTypeId) {
                        ContentTypeId.TOURIST_ATTRACTION.contentTypeCode.toString() -> "관광지"
                        ContentTypeId.RESTAURANT.contentTypeCode.toString() -> "음식점"
                        ContentTypeId.ACCOMMODATION.contentTypeCode.toString() -> "숙소"
                        else -> ""
                    },
                    itemDate = scheduleDate,
                    itemLongitude = tripItemModel.mapLong,
                    itemLatitude = tripItemModel.mapLat,
                    itemContentId = tripItemModel.contentId,
                )

                tripScheduleService.addTripItemToSchedule(tripScheduleDocId, scheduleDate, scheduleItem)
            }.await()
            // ✅ 현재 화면을 닫고 이전 화면으로 돌아감
            application.navHostController.popBackStack(
                "${ScheduleScreenName.SCHEDULE_DETAIL_SCREEN.name}?tripScheduleDocId={tripScheduleDocId}&areaName={areaName}&areaCode={areaCode}",
                false
            )
        }
    }
}
