package com.lion.wandertrip.presentation.detail_page.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lion.wandertrip.R
import com.lion.wandertrip.component.CustomRatingBar
import com.lion.wandertrip.presentation.detail_page.DetailViewModel
import com.lion.wandertrip.util.CustomFont
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun IntroColumn(detailViewModel: DetailViewModel, contentId: String) {
    Log.d("test100", "contentId")
    val sw = detailViewModel.tripApplication.screenWidth
    val sh = detailViewModel.tripApplication.screenHeight
    val tripCommonContentValue = detailViewModel.tripCommonContentModelValue.value


    LaunchedEffect(Unit) {
        detailViewModel.getContentModel(contentId)
        detailViewModel.gettingCityName(
            detailViewModel.tripCommonContentModelValue.value.areaCode ?: "",
            detailViewModel.tripCommonContentModelValue.value.siGunGuCode ?: "",
        )
        detailViewModel.isLikeContent(contentId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            text = tripCommonContentValue.title ?: "",
            fontSize = 26.sp,
            fontFamily = CustomFont.customFontBold,
        )

        Spacer(modifier = Modifier.height(16.dp)) // 간격

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 별점 표시
            CustomRatingBar(detailViewModel.contentValue.value.ratingScore)
            Text(
                "(${detailViewModel.contentValue.value.ratingScore})",
                fontFamily = CustomFont.customFontRegular
            )
            Text(
                " ${detailViewModel.reviewCountValue.value}명이 추천중!.",
                fontFamily = CustomFont.customFontRegular
            )
        }


        Spacer(modifier = Modifier.height(16.dp)) // 간격

        // 로케이션 아이콘과 텍스트
        if (detailViewModel.cityNameValue.value != "")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "로케이션 아이콘",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Gray
                )
                // model 에 area코드, sigungu code 없어 못가져옴 추가할지 버릴지 선택해야함
                Text(
                    detailViewModel.cityNameValue.value,
                    fontSize = 16.sp,
                    fontFamily = CustomFont.customFontRegular
                )
            }

        Spacer(modifier = Modifier.height(16.dp)) // 간격

        // 이미지 (전체 width, 높이는 300dp)
        GlideImage(
            imageModel = tripCommonContentValue.firstImage,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height((sh / 9).dp)
                .clip(RoundedCornerShape(8.dp)),  // 이미지 둥글게 만들기
            circularReveal = CircularReveal(duration = 250),
            placeHolder = ImageBitmap.imageResource(R.drawable.img_image_holder),
        )
        Spacer(modifier = Modifier.height(8.dp)) // 간격


        // Row에 저장하기 아이콘과 일정 추가 아이콘
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            val img = when (detailViewModel.isLikeContentValue.value) {
                true -> R.drawable.ic_heart_filled_24px
                false -> R.drawable.ic_heart_empty_24px
            }
            DetailColumnIconAndText(
                ImageVector.vectorResource(img),
                "저장하기", onClick = {
                    detailViewModel.onClickIconIsLikeContent(contentId)
                },true
            )
            DetailColumnIconAndText(
                ImageVector.vectorResource(R.drawable.ic_calendar_add_on_24px),
                "일정추가", onClick = {
                    detailViewModel.onClickIconAddSchedule()
                }
            )
        }
    }



    Spacer(modifier = Modifier.height(16.dp)) // 간격

    // 지역 소개 (길게)
    Text(
        text = detailViewModel.tripCommonContentModelValue.value.overview!!,
        fontSize = 16.sp,
        fontFamily = CustomFont.customFontRegular
    )
}

