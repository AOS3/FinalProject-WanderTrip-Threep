package com.lion.wandertrip.presentation.bottom.my_info_page.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collection.mutableVectorOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lion.wandertrip.R
import com.lion.wandertrip.presentation.bottom.my_info_page.MyInfoViewModel
import com.lion.wandertrip.ui.theme.Gray2
import com.lion.wandertrip.ui.theme.Gray3
import com.lion.wandertrip.ui.theme.Gray4
import com.lion.wandertrip.ui.theme.LightGray1
import com.lion.wandertrip.ui.theme.LightGray2
import com.lion.wandertrip.ui.theme.LightGray3
import com.lion.wandertrip.ui.theme.PastelBlue
import com.lion.wandertrip.ui.theme.PastelLightBlue
import com.lion.wandertrip.ui.theme.PastelSkyBlue
import com.lion.wandertrip.ui.theme.pastelColors
import com.lion.wandertrip.util.CustomFont
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun ProfileCardBasicImage(
    userNickName: String, viewModel: MyInfoViewModel
) {

    LaunchedEffect(viewModel.userModelValue.value.userProfileImageURL) {

    }
    // 카드뷰
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = LightGray3)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            // 상단 영역 (닉네임 + 프로필 편집 버튼 + 프로필 이미지)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp), // 바닥만 30dp 패딩 적용
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    // 닉네임
                    Text(
                        text = userNickName,
                        fontSize = 24.sp,
                        fontFamily = CustomFont.customFontBold,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            // 편집화면 전환
                            viewModel.onClickTextUserInfoModify()
                        }) {
                        Text(
                            text = "유저 정보 수정",
                            fontSize = 18.sp,
                            fontFamily = CustomFont.customFontBold,
                            color = Gray4
                        )
                        // 편집 아이콘
                        Icon(
                            painter = painterResource(id = R.drawable.ic_small_arrow_forward_24px), // 뒤로가기 아이콘
                            contentDescription = "Back",
                            modifier = Modifier.size(24.dp),
                            tint = Gray4 // Gray 3: 진한 그레이 아이콘 색상 적용
                        )

                    }
                }
                GlideImage(
                    imageModel = if (viewModel.showImageUri.value != null) {
                        viewModel.showImageUri.value
                    } else {
                        R.drawable.img_basic_profile_image
                    },
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                        .clip(RoundedCornerShape(8.dp)),  // 이미지 둥글게 만들기
                    circularReveal = CircularReveal(duration = 250),
                    placeHolder = ImageBitmap.imageResource(R.drawable.img_image_holder),
                )
            }

        }

        Spacer(modifier = Modifier.height(16.dp))
        val iconList = listOf(
            Pair(R.drawable.ic_luggage_24px, "내 여행") to { viewModel.onClickIconMyTrip() },
            Pair(R.drawable.ic_bookmark_24px, "내 저장") to { viewModel.onClickIconMyInteresting() },
            Pair(R.drawable.ic_star_24px, "내 리뷰") to { viewModel.onClickIconMyReview() },
            Pair(R.drawable.ic_camera_film_24px, "내 여행기") to { viewModel.onClickIconTripNote() }
        )
        // 아이콘 + 텍스트 Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            iconList.forEach { (iconResText, onClick) ->
                val (iconRes, text) = iconResText
                ColumnIconAndText(
                    icon = ImageVector.vectorResource(id = iconRes),
                    text = text,
                    onClick = onClick
                )
            }
        }
    }
}

