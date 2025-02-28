package com.lion.wandertrip.presentation.bottom.my_info_page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lion.a02_boardcloneproject.component.CustomDividerComponent
import com.lion.wandertrip.presentation.bottom.my_info_page.components.HorizontalRecentPostsList
import com.lion.wandertrip.presentation.bottom.my_info_page.components.HorizontalScheduleList
import com.lion.wandertrip.presentation.bottom.my_info_page.components.ProfileCardBasicImage
import com.lion.wandertrip.ui.theme.pastelBlueColors
import com.lion.wandertrip.util.CustomFont

@Composable
fun MyInfoScreen(myInfoViewModel: MyInfoViewModel = hiltViewModel()) {
    Log.d("myScreen", "마이페이지")
    LaunchedEffect(Unit) {
        myInfoViewModel.gettingUserModel()
        // 화면 열때 리스트 가져오기
        myInfoViewModel.getTripScheduleList()
        // 최근 본 목록 가져오기
        myInfoViewModel.getRecentTripItemList()
    }
    val userModel = myInfoViewModel.userModelValue.value
    Scaffold(
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,  // 콘텐츠를 상단에 배치
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileCardBasicImage(
                userNickName = userModel.userNickName,
                viewModel = myInfoViewModel
            )

            Spacer(modifier = Modifier.height(16.dp))  // 프로필 카드와 일정 리스트 사이 간격

            // 일정 리스트
            HorizontalScheduleList(myInfoViewModel, myInfoViewModel.recentScheduleList)

            Column {
                Text("최근 본 항목", fontFamily = CustomFont.customFontBold)
                // 최근 게시글 리스트
                HorizontalRecentPostsList(
                    myInfoViewModel.recentTripItemList,
                    myInfoViewModel
                )
            }



        }
    }
}