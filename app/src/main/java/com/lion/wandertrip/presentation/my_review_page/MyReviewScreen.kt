package com.lion.wandertrip.presentation.my_review_page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.lion.wandertrip.presentation.my_review_page.components.VerticalMyReviewList

@Composable
fun MyReviewScreen(myReviewViewModel: MyReviewViewModel = hiltViewModel()) {
    Log.d("MyReviewScreen","MyReviewScreen shot")
    LaunchedEffect (Unit){
        myReviewViewModel.isLoading.value=true
        myReviewViewModel.getReviewList()
    }

    Log.d("test", "MyReviewScreen")
    if (myReviewViewModel.isLoading.value) {
        LottieLoadingIndicator()
    } else {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                navigationIconOnClick = {
                    myReviewViewModel.onClickNavIconBack()
                },
                navigationIconImage = ImageVector.vectorResource(R.drawable.ic_arrow_back_24px),
                title = "내 리뷰",
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
                VerticalMyReviewList(myReviewViewModel)
            }

        }
    }
}