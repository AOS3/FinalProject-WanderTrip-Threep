package com.lion.wandertrip.presentation.detail_page.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lion.wandertrip.presentation.detail_page.DetailViewModel
import com.lion.wandertrip.util.CustomFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetReviewFilter(detailViewModel: DetailViewModel) {
    ModalBottomSheet(
        onDismissRequest = { detailViewModel.isReviewOptionSheetOpen.value = false }
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
            Text(
                "정렬", modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                fontFamily = CustomFont.customFontBold,
                fontSize = 24.sp
            )
            Text(
                "최신순", modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable {
                        detailViewModel.onClickTextRecentFilter()
                        detailViewModel.isReviewOptionSheetOpen.value = false
                    },
                fontFamily = CustomFont.customFontRegular,
                fontSize = 16.sp
            )
            Text(
                "별점 낮은순", modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable {
                        detailViewModel.onClickTextRatingAsc()
                        detailViewModel.isReviewOptionSheetOpen.value = false
                    },
                fontFamily = CustomFont.customFontRegular,
                fontSize = 16.sp
            )
            Text(
                "별점  높은순", modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable {
                        detailViewModel.onClickTextRatingDesc()
                        detailViewModel.isReviewOptionSheetOpen.value = false
                    },
                fontFamily = CustomFont.customFontRegular,
                fontSize = 16.sp
            )
        }
    }
}