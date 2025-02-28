package com.lion.wandertrip.presentation.search_result_page

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lion.wandertrip.TripApplication
import com.lion.wandertrip.model.TripItemModel
import com.lion.wandertrip.model.TripNoteModel
import com.lion.wandertrip.presentation.main_page.MainScreen
import com.lion.wandertrip.repository.TripAreaBaseItemRepository
import com.lion.wandertrip.service.TripAreaBaseItemService
import com.lion.wandertrip.service.TripNoteService
import com.lion.wandertrip.util.BotNavScreenName
import com.lion.wandertrip.util.ContentTypeId
import com.lion.wandertrip.util.MainScreenName
import com.lion.wandertrip.util.TripNoteScreenName
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    @ApplicationContext context: Context,
    val tripAreaBaseItemService: TripAreaBaseItemService,
    val tripNoteService: TripNoteService
): ViewModel() {

    val tripApplication = context as TripApplication

    private val _searchResults = MutableLiveData<List<TripItemModel>>() // ✅ 검색 결과 LiveData 추가
    val searchResults: LiveData<List<TripItemModel>> get() = _searchResults

    private val _searchNoteResults = MutableLiveData<List<TripNoteModel>>() // ✅ 검색 결과 LiveData 추가
    val searchNoteResults: LiveData<List<TripNoteModel>> get() = _searchNoteResults

    private val _isLoading = MutableLiveData(false) // ✅ 로딩 상태 추가
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun searchTrip(text: String) {
        viewModelScope.launch {
            _isLoading.value = true // ✅ 검색 시작 전 로딩 시작
            val items = tripAreaBaseItemService.getTripAllItem()
            val noteItems = tripNoteService.gettingTripNoteList()
            val filteredItems = items?.filter { item ->
                item.title.contains(text, ignoreCase = true) ||
                        item.cat1.contains(text, ignoreCase = true) ||
                        item.cat2.contains(text, ignoreCase = true) ||
                        item.cat3.contains(text, ignoreCase = true)
            } ?: emptyList()

            val filteredNoteItems = noteItems.filter { item ->
                item.tripNoteTitle.contains(text, ignoreCase = true) ||
                        item.tripNoteContent.contains(text, ignoreCase = true)
            } ?: emptyList()

            _searchResults.value = filteredItems
            _searchNoteResults.value = filteredNoteItems
            _isLoading.value = false // ✅ 검색 완료 후 로딩 종료
        }
    }

    fun onNavigateDetail(contentId: String) {
        tripApplication.navHostController.navigate("${MainScreenName.MAIN_SCREEN_DETAIL.name}/$contentId")
    }

    fun onNavigateTripNote(documentId: String) {
        tripApplication.navHostController.navigate("${TripNoteScreenName.TRIP_NOTE_DETAIL.name}/$documentId")
    }

    fun onNavigateBackToSearchScreen() {
        tripApplication.navHostController.popBackStack(
            route = MainScreenName.MAIN_SCREEN_SEARCH.name, // ✅ 검색창 화면으로 이동
            inclusive = false // ✅ 기존 검색창을 새로 생성하도록 설정
        )
    }
}