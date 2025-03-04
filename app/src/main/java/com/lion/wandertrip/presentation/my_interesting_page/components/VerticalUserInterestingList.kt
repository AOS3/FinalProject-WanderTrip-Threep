package com.lion.wandertrip.presentation.my_interesting_page.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lion.wandertrip.R
import com.lion.wandertrip.component.CustomRatingBar
import com.lion.wandertrip.model.UserInterestingModel
import com.lion.wandertrip.presentation.my_interesting_page.MyInterestingViewModel
import com.lion.wandertrip.ui.theme.Gray0
import com.lion.wandertrip.ui.theme.NanumSquareRoundRegular
import com.lion.wandertrip.util.CustomFont
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun VerticalUserInterestingList(
    viewModel: MyInterestingViewModel,
    items: List<UserInterestingModel>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(items) { item ->
            UserInterestingItem(viewModel, interestingItem = item, items.indexOf(item))
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}


@Composable
fun UserInterestingItem(
    viewModel: MyInterestingViewModel,
    interestingItem: UserInterestingModel,
    pos: Int
) {

    // contentId를 기억하도록 합니다
    val contentId = interestingItem.contentID

    // 'LaunchedEffect'로 contentId 변경 시에만 호출되도록 처리
    LaunchedEffect(contentId) {
        viewModel.isLikeContent(contentId)
    }

    // isLike 상태를 가져옵니다
    val isLike = viewModel.likeMap[pos] // 이 부분은 viewModel에서 상태를 미리 처리하도록 수정



    Row(
        modifier = Modifier
            .clickable {
                viewModel.onClickListItemToDetailScreen(interestingItem.contentID)
            }
            .fillMaxWidth()
            .background(Gray0, RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 좌측: 텍스트 정보
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = interestingItem.contentTitle,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = CustomFont.customFontBold
            )
            Spacer(modifier = Modifier.height(4.dp))

            CustomRatingBar(interestingItem.ratingScore)
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "저장 ${interestingItem.saveCount}회/추천 : ${interestingItem.starRatingCount}",
                fontSize = 14.sp,
                color = Color.Gray,
                fontFamily = CustomFont.customFontRegular
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "주소: ${interestingItem.addr1} ${interestingItem.addr2}",
                fontSize = 14.sp,
                color = Color.Gray,
                fontFamily = NanumSquareRoundRegular
            )
        }

        // 우측: 이미지 + 하트 아이콘
        Box(
            modifier = Modifier
                .size(80.dp)
                .padding(start = 10.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
                .clickable {
                    viewModel.onClickIconHeart(contentId,pos)
                },
            contentAlignment = Alignment.TopEnd
        ) {
            if (interestingItem.smallImagePath.isNotEmpty()) {
                // 이미지
                GlideImage(
                    imageModel = interestingItem.smallImagePath,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight(),  // 이미지 둥글게 만들기
                    circularReveal = CircularReveal(duration = 250), // 애니메이션 효과 원형 모양으로 이미지 로드
                    placeHolder = ImageBitmap.imageResource(R.drawable.img_image_holder),
                )
            }
            val vector = when (isLike) {
                true -> R.drawable.ic_heart_filled_24px
                false -> R.drawable.ic_heart_empty_24px
                null -> TODO()
            }
            Icon(
                imageVector = ImageVector.vectorResource(vector),
                contentDescription = "Save",
                tint = Color.Red,
                modifier = Modifier
                    .padding(4.dp)
                    .size(24.dp)
            )
        }
    }
}