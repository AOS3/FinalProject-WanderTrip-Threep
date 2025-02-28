package com.lion.wandertrip.presentation.bottom.my_info_page.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lion.wandertrip.R
import com.lion.wandertrip.model.RecentTripItemModel
import com.lion.wandertrip.model.TripItemModel
import com.lion.wandertrip.presentation.bottom.my_info_page.MyInfoViewModel
import com.lion.wandertrip.ui.theme.LightGray1
import com.lion.wandertrip.ui.theme.LightGray2
import com.lion.wandertrip.ui.theme.LightGray3
import com.lion.wandertrip.ui.theme.LightGray4
import com.lion.wandertrip.util.ContentTypeId
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun HorizontalRecentPostsList(tripItemList: MutableList<RecentTripItemModel>,myInfoViewModel: MyInfoViewModel) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(tripItemList) { tripItem ->
            TouristItem(tripItem,myInfoViewModel)
        }
    }
}

@Composable
fun TouristItem(tripItem: RecentTripItemModel,myInfoViewModel: MyInfoViewModel) {
    // 12: 관광지, 32: 숙박, 39: 음식점
    // 글자 줄이기
    val shortedTitle = if (tripItem.title.length > 6) {
        tripItem.title.take(6) + "..."
    } else {
        tripItem.title
    }

    // 지역 타입으로 나누기

    val spotType = when(tripItem.contentTypeID){
        "${ContentTypeId.TOURIST_ATTRACTION.contentTypeCode}"->{"관광명소"}
        "${ContentTypeId.ACCOMMODATION.contentTypeCode}"->{"숙박시설"}
        else -> {"식당"}
    }
    Card(
        colors = CardDefaults.cardColors(containerColor = LightGray3),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
                myInfoViewModel.onClickCardRecentContent(tripItem.contentID)
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)  // 아이템 간 간격 조정
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                // 제목 텍스트
                Text(
                    text = shortedTitle,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)  // 텍스트 영역이 여유 공간을 차지하도록 설정
                )

                // 지역 타입
                Text(
                    text = spotType,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)  // 텍스트 영역이 여유 공간을 차지하도록 설정
                )
            }
            // 이미지
            GlideImage(
                imageModel = tripItem.imageUri,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)  // 이미지 크기 설정
                    .clip(RoundedCornerShape(8.dp)),  // 이미지 둥글게 만들기
                circularReveal = CircularReveal(duration = 250),
                placeHolder = ImageBitmap.imageResource(R.drawable.img_image_holder),
            )
        }
    }
}