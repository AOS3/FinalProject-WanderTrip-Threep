package com.lion.wandertrip.presentation.schedule_detail_page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.hilt.navigation.compose.hiltViewModel
import com.lion.wandertrip.component.LottieLoadingIndicator
import com.lion.wandertrip.model.TripItemModel
import com.lion.wandertrip.presentation.schedule_detail_page.component.ScheduleDetailDateList
import com.lion.wandertrip.ui.theme.NanumSquareRound
import com.lion.wandertrip.util.SharedTripItemList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleDetailScreen(
    tripScheduleDocId: String,
    areaName: String,
    areaCode: Int,
    viewModel: ScheduleDetailViewModel = hiltViewModel(),
) {
    val isFirstLaunch = rememberSaveable { mutableStateOf(true) } // ✅ 처음 실행 여부 저장
    val isLoading by viewModel.isLoading // ✅ 로딩 상태 가져오기

    LaunchedEffect(Unit) {
        viewModel.addAreaData(tripScheduleDocId, areaName, areaCode)
        if (isFirstLaunch.value) { // ✅ 처음 실행될 때만 실행
            viewModel.getTripSchedule()
            isFirstLaunch.value = false // ✅ 이후에는 실행되지 않도록 설정
        }
        
        // 공공데이터 포털에서 받아온 여행지 목록 데이터 초기화
        SharedTripItemList.sharedTripItemList.clear()
        // 선택된 여행지 목록 (룰렛 항목) 초기화
        SharedTripItemList.rouletteItemList.clear()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = Color.White,
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.White
                    ),
                    title = {
                        Text(text = viewModel.tripSchedule.value.scheduleTitle, fontFamily = NanumSquareRound)
                    },
                    navigationIcon = {
                        IconButton(onClick = { viewModel.backScreen() }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "뒤로 가기")
                        }
                    },
                    actions = {
                        IconButton(onClick = { viewModel.moveToScheduleDetailFriendsScreen(tripScheduleDocId) }) {
                            Icon(imageVector = Icons.Filled.People, contentDescription = "함께 하는 사람 목록")
                        }
                    },
                )
            }
        ) {
            Column(modifier = Modifier.padding(it)) {
                ScheduleDetailDateList(
                    viewModel = viewModel,
                    tripSchedule = viewModel.tripSchedule.value,
                    formatTimestampToDate = { timestamp -> viewModel.formatTimestampToDate(timestamp) }
                )
            }
        }

        // ✅ 로딩 화면 추가 (투명 오버레이)
        if (isLoading) {
            LottieLoadingIndicator() // ✅ 로딩 애니메이션
        }
    }
}