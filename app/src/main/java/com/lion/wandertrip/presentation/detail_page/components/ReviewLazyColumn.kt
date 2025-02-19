package com.lion.wandertrip.presentation.detail_page.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lion.a02_boardcloneproject.component.CustomIconButton
import com.lion.wandertrip.R
import com.lion.wandertrip.presentation.detail_page.DetailViewModel

@Composable
fun ReviewLazyColumn(detailViewModel: DetailViewModel) {
    val sh = detailViewModel.tripApplication.screenHeight
    // 최초 한 번만 실행
    LaunchedEffect(Unit) {
        detailViewModel.reviewList.clear()
        detailViewModel.getReviewModel()
        detailViewModel.getFilteredReviewList()
    }
    val contentModel = detailViewModel.contentModelValue.value
    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height((sh / 3).dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "리뷰 : ${detailViewModel.filteredReviewList.size}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
                CustomIconButton(
                    ImageVector.vectorResource(R.drawable.ic_add_24px),
                    iconButtonOnClick = {
                        // 리뷰 쓰기 화면 띄우기
                        detailViewModel.onClickIconReviewWrite(contentModel.contentID,contentModel.detailTitle)
                    })
            }
        }
        item {
            Text(
                text = "${detailViewModel.filterStateStringValue.value} ▼",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .clickable {
                        detailViewModel.onClickTextReviewFilter()
                    }
            )
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        Log.d("test100", "VerticalReviewList 호출 : ")
        item {
            VerticalReviewList(detailViewModel)
        }
    }


}
