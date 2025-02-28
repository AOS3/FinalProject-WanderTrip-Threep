package com.lion.wandertrip.presentation.my_interesting_page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lion.a02_boardcloneproject.component.CustomTopAppBar
import com.lion.wandertrip.R
import com.lion.wandertrip.component.LottieLoadingIndicator
import com.lion.wandertrip.presentation.my_interesting_page.components.BottomSheetAreaFilter
import com.lion.wandertrip.presentation.my_interesting_page.components.CityDropdownButton
import com.lion.wandertrip.presentation.my_interesting_page.components.CustomChipButton
import com.lion.wandertrip.presentation.my_interesting_page.components.VerticalUserInterestingList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyInterestingScreen(myInterestingViewModel: MyInterestingViewModel = hiltViewModel()) {
    Log.d("test","MyInterestingScreen")
    // Scroll 상태를 기억하기 위한 rememberScrollState 사용
    val scrollState = rememberScrollState()

    // ✅ 최초 실행 시 데이터 불러오기 (한 번만 실행)
    // 매개변수 Unit 전달 시 최초 1회만 생성
    LaunchedEffect(Unit) {
        Log.d("asd","LaunchedEffect")
        myInterestingViewModel.interestingListAll.clear()
        if(myInterestingViewModel.isLoading.value== false){
            myInterestingViewModel.getInterestingList()

        }
    }

    // ✅ filteredCityName이 변경될 때만 실행 (무한 루프 방지)
    // 매개변수가 Any 타입이면 변수가 변경될때만 실행, composable이 재구성 되더라도 실행되지 않음
    LaunchedEffect(myInterestingViewModel.filteredCityName.value) {
        myInterestingViewModel.getInterestingFilter(myInterestingViewModel.filteredCityName.value)
    }

    if(myInterestingViewModel.isLoading.value){
        Log.d("test","로딩중")
        LottieLoadingIndicator()
    }else{
        Log.d("test","로딩중아님")
        Scaffold(
            topBar = {
                CustomTopAppBar(
                    navigationIconOnClick = {
                        myInterestingViewModel.onClickNavIconBack()
                    },
                    navigationIconImage = ImageVector.vectorResource(R.drawable.ic_arrow_back_24px),
                    title = "내 저장",
                )
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(scrollState),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    CityDropdownButton(myInterestingViewModel) {
                        myInterestingViewModel.isSheetOpen.value = true
                    }
                    CustomChipButton("관광지", {
                        myInterestingViewModel.onClickButtonAttraction()
                    }, myInterestingViewModel.isCheckAttraction)
                    CustomChipButton("식당", {
                        myInterestingViewModel.onClickButtonRestaurant()
                    }, myInterestingViewModel.isCheckRestaurant)
                    CustomChipButton("숙소", {
                        myInterestingViewModel.onClickButtonAccommodation()
                    }, myInterestingViewModel.isCheckAccommodation)
                }

                VerticalUserInterestingList(myInterestingViewModel,myInterestingViewModel.interestingListFilterByCity)
            }

            // BottomSheet가 표시될 때의 설정
            if (myInterestingViewModel.isSheetOpen.value) {
               BottomSheetAreaFilter(myInterestingViewModel)
            }
        }
    }

}
