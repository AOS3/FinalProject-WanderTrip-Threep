package com.lion.wandertrip.presentation.schedule_detail_page

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.lion.a02_boardcloneproject.component.CustomTopAppBar
import com.lion.wandertrip.ui.theme.NanumSquareRound

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleDetailScreen(
    areaName: String,
    areaCode: Int,
    viewModel: ScheduleDetailViewModel = hiltViewModel(),
) {

    Log.d("ScheduleDetailScreen", "날짜 리스트 : ${viewModel.tripSchedule.scheduleDateList}")

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White // ✅ 투명색 적용
                ),
                title = {
                    Text(text = "일정 상세", fontFamily = NanumSquareRound) // ✅ 제목 설정
                },
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.backScreen() }
                    ) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "뒤로 가기")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {  }
                    ) {
                        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "더보기")
                    }
                },
            )

        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            Text(text = "도시 이름 : $areaName")
            Text(text = "도시 코드 : $areaCode")
        }
    }

}