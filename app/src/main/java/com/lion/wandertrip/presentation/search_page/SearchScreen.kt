package com.lion.wandertrip.presentation.search_page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lion.wandertrip.model.TripItemModel
import com.lion.wandertrip.presentation.search_page.component.HomeSearchBar
import com.lion.wandertrip.presentation.search_page.component.RecentItem

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    // 🔍 검색어 상태
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color.White,
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // 🔍 수정된 검색 바 적용
            HomeSearchBar(
                query = searchQuery,
                onSearchQueryChanged = { searchQuery = it },
                onSearchClicked = { if (searchQuery.isNotBlank()) {
                    // 🔹 최근 검색어 저장
                    val searchItem = TripItemModel(title = searchQuery)
                    viewModel.addSearchToRecent(searchItem)

                    // 🔹 검색 결과 화면으로 이동
                    viewModel.onClickToResult(searchQuery)
                } },
                onClearQuery = { searchQuery = "" }, // X 버튼 클릭 시 전체 목록 표시
                onBackClicked = { viewModel.backScreen() } // 🔙 뒤로 가기 버튼 클릭 시 동작
            )

        // ✅ 최근 검색어 목록 추가
            RecentItem(searchViewModel = viewModel)
        }
    }
}


