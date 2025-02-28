package com.lion.wandertrip.presentation.bottom.home_page

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lion.wandertrip.component.LottieLoadingIndicator
import com.lion.wandertrip.model.UserModel
import com.lion.wandertrip.presentation.bottom.home_page.components.PopularTripItem
import com.lion.wandertrip.presentation.bottom.home_page.components.TripSpotItem
import com.lion.wandertrip.ui.theme.NanumSquareRound

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {


    val tripItems by viewModel.randomTourItems.observeAsState(emptyList())
    val topTrips by viewModel.topScrapedTrips.observeAsState(emptyList())
    val imageUrlMap = viewModel.imageUrlMap
    val isLoading by viewModel.isLoading.observeAsState(false) // ✅ 로딩 상태 감지
    val userModel by viewModel.userModel.observeAsState(UserModel(userDocId = "", userLikeList = emptyList()))

    LaunchedEffect(Unit) {
        viewModel.fetchTripNotes()
        viewModel.getTopScrapedTrips()
        viewModel.fetchRandomTourItems()
    }

    if (isLoading) {
        // ✅ 로딩 중일 때 표시할 화면
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LottieLoadingIndicator() // ✅ 로딩 UI 표시
        }
    } else {
        // ✅ 로딩 완료 후 실제 화면 표시
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
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Text(
                            text = "추천 관광지",
                            fontSize = 20.sp,
                            fontFamily = NanumSquareRound,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    items(tripItems) { tripItem ->
                        TripSpotItem(
                            tripItem = tripItem,
                            onItemClick = { viewModel.onClickTrip(tripItem.contentId) },
                            userModel = userModel,
                            onFavoriteClick = { contentId -> viewModel.toggleFavorite(contentId) }
                        )
                    }

                    item {
                        Text(
                            text = "🔥 인기 많은 여행기",
                            fontSize = 20.sp,
                            fontFamily = NanumSquareRound,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    items(topTrips) { tripNote ->
                        PopularTripItem(
                            tripItem = tripNote,
                            imageUrl = imageUrlMap[tripNote.tripNoteImage.firstOrNull()],
                            onItemClick = { viewModel.onClickTripNote(tripNote.tripNoteDocumentId) }
                        )
                    }
                }
            }
        }
    }
}