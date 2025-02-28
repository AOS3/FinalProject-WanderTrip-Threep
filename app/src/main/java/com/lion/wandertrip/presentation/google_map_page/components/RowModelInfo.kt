package com.lion.wandertrip.presentation.google_map_page.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lion.a02_boardcloneproject.component.CustomIconButton
import com.lion.wandertrip.R
import com.lion.wandertrip.component.CustomRatingBar
import com.lion.wandertrip.model.DetailModel
import com.lion.wandertrip.presentation.google_map_page.GoogleMapViewModel
import com.lion.wandertrip.util.CustomFont
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun RowModelInfo(googleMapViewModel: GoogleMapViewModel, contentModel : DetailModel) {
    val sh = googleMapViewModel.tripApplication.screenHeight
    val sw = googleMapViewModel.tripApplication.screenWidth

    // 하단에 지역 정보 표시
    Row(modifier = Modifier.padding(8.dp)) {
        // 좌측 이미지
        GlideImage(
            imageModel = contentModel.detailImage,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height((sh/22).dp)
                .width((sw/13).dp),  // 이미지 둥글게 만들기
            circularReveal = CircularReveal(duration = 250), // 애니메이션 효과 원형 모양으로 이미지 로드
            placeHolder = ImageBitmap.imageResource(R.drawable.img_image_holder),
        )

        Spacer(modifier = Modifier.width(16.dp))

        // 우측 정보 컬럼
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterVertically)
        ) {
            // 관광지 타이틀
            Text(
                text = contentModel.detailTitle,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = CustomFont.customFontBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis // 넘칠 경우 "..." 표시
            )


            // 관광지 설명
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = contentModel.detailDescription,
                fontSize = 14.sp,
                color = Color.Gray,
                fontFamily = CustomFont.customFontRegular,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis // 넘칠 경우 "..." 표시
            )

            Spacer(modifier = Modifier.height(8.dp))
            // 별점 아이콘, 좋아요 수, 별점 수
            Row(verticalAlignment = Alignment.CenterVertically) {
                // 별점 아이콘
                CustomRatingBar(contentModel.detailRating)
                Spacer(modifier = Modifier.width(4.dp))
                // 별점 수
                Text(
                    text = "${contentModel.ratingCount}",
                    fontSize = 14.sp,
                    fontFamily = CustomFont.customFontRegular

                )

                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Like",
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.width(4.dp))


                // 좋아요 수
                Text(
                    text = "${contentModel.likeCnt}",
                    fontSize = 14.sp,
                    fontFamily = CustomFont.customFontRegular,
                )
            }
        }
    }
}