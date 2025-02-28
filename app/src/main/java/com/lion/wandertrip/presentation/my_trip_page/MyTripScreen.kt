package com.lion.wandertrip.presentation.my_trip_page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lion.a02_boardcloneproject.component.CustomTopAppBar
import com.lion.wandertrip.R
import com.lion.wandertrip.presentation.my_trip_page.components.VerticalTripItemList

@Composable
fun MyTripScreen(myTripViewModel: MyTripViewModel = hiltViewModel()) {
    Log.d("test100", "MyTripScreen")
    myTripViewModel.getTripList()
    Scaffold(
        topBar = {
            CustomTopAppBar(
                navigationIconOnClick = {
                    myTripViewModel.onClickNavIconBack()
                },
                navigationIconImage = ImageVector.vectorResource(R.drawable.ic_arrow_back_24px),
                title = "내 여행",
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
            VerticalTripItemList(myTripViewModel)
        }

    }

}