package com.lion.wandertrip.presentation.schedule_select_item.roulette_item.roulette_item_select

import ScheduleItemCategoryChips
import ScheduleItemSearchBar
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lion.wandertrip.presentation.schedule_select_item.roulette_item.roulette_item_select.component.TripItemList
import com.lion.wandertrip.ui.theme.NanumSquareRoundRegular
import com.lion.wandertrip.util.SharedTripItemList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouletteItemSelectScreen(
    navController: NavHostController,
    viewModel: RouletteItemSelectViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.observeUserLikeList()
    }

    // ✅ 기존 선택된 항목 유지 (State 사용)
    var selectedItems by remember { mutableStateOf(SharedTripItemList.rouletteItemList.toList()) }

    // 🔍 검색어 상태
    var searchQuery by remember { mutableStateOf("") }
    // 선택된 카테고리 상태
    var selectedCategoryCode by remember { mutableStateOf<String?>(null) }
    
    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text("여행지 선택", fontFamily = NanumSquareRoundRegular) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "뒤로 가기")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent // ✅ 투명색 적용
                ),
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // 🔍 검색 바 추가
            ScheduleItemSearchBar(
                query = searchQuery,
                onSearchQueryChanged = { searchQuery = it },
                onSearchClicked = {},
                onClearQuery = { searchQuery = "" }
            )

            // ★ 랜덤으로 30개 선택 버튼 추가
            Button(
                onClick = {
                    // sharedTripItemList에서 임의로 섞은 후 30개 선택
                    selectedItems = SharedTripItemList.sharedTripItemList.shuffled().take(30)
                    Log.d("RouletteItemSelectScreen", "Randomly selected ${selectedItems.size} items")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("랜덤 30개 선택")
            }

            // 🎯 카테고리 칩 버튼 추가
            ScheduleItemCategoryChips(
                itemCode = SharedTripItemList.sharedTripItemList[0].contentTypeId.toInt(),
                selectedCategoryCode = selectedCategoryCode,
                onCategorySelected = { newCategoryCode ->
                    selectedCategoryCode = newCategoryCode
                }
            )

            // ✅ 필터링된 여행지 리스트
            val filteredList = SharedTripItemList.sharedTripItemList.filter {
                val matchesCategory = when (SharedTripItemList.sharedTripItemList[0].contentTypeId.toInt()) {
                    12 -> selectedCategoryCode == null || it.cat2 == selectedCategoryCode
                    39, 32 -> selectedCategoryCode == null || it.cat3 == selectedCategoryCode
                    else -> true
                }
                val matchesSearchQuery = it.title.contains(searchQuery, ignoreCase = true)
                matchesCategory && matchesSearchQuery
            }

            // TripItemList 컴포넌트
            TripItemList(
                tripItems = filteredList,
                selectedItems = selectedItems,
                viewModel,
                onItemClick = { tripItem ->
                    selectedItems = if (selectedItems.contains(tripItem)) {
                        selectedItems - tripItem
                    } else {
                        selectedItems + tripItem
                    }
                },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // ✅ 선택된 항목을 ViewModel의 `rouletteItemList`에 저장
                    viewModel.updateRouletteItemList(selectedItems)
                    selectedItems.forEach {
                        Log.d("RouletteItemSelectScreen", "Selected Item: ${it.title}")
                    }
                    Log.d("RouletteItemSelectScreen", "Selected ${selectedItems.size} items")
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = selectedItems.isNotEmpty()
            ) {
                Text("추가")
            }
        }
    }
}