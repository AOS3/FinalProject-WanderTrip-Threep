package com.lion.wandertrip.presentation.bottom.home_page

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lion.wandertrip.model.SimpleTripItemModel
import com.lion.wandertrip.presentation.bottom.home_page.components.PopularTripItem
import com.lion.wandertrip.presentation.bottom.home_page.components.TravelSpotItem
import com.lion.wandertrip.util.ContentTypeId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val tripItems by viewModel.tripItemList.observeAsState(emptyList())
    val topTrips by viewModel.topScrapedTrips.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.getTopScrapedTrips()
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                modifier = Modifier.height(56.dp),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF0077C2),
                    titleContentColor = Color.White
                ),
                title = {},
                actions = {
                    IconButton(
                        onClick = { viewModel.onClickIconSearch() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "검색",
                            tint = Color.White
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // ✅ TopBar 높이만큼 여백 추가
        ) {
            // ✅ 스크롤 가능하도록 설정
            LazyColumn(
                modifier = Modifier
                    .weight(1f) // ✅ 스크롤 가능하도록 조정
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // "추천 관광지" 섹션
                item {
                    Text(
                        text = "추천 관광지",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                items(tripItems) { tripItem ->
                    TravelSpotItem(
                        tripItem = tripItem,
                        onItemClick = { viewModel.onClickTrip(tripItem.contentId) }
                    )
                }

                // "🔥 인기 많은 여행기" 섹션
                item {
                    Text(
                        text = "🔥 인기 많은 여행기",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                items(topTrips) { tripNote ->
                    PopularTripItem(
                        tripItem = tripNote,
                        onItemClick = { viewModel.onClickTripNote(tripNote.tripNoteDocumentId) }
                    )
                }
            }
        }
    }
}