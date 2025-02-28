package com.lion.wandertrip.presentation.detail_page.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lion.a02_boardcloneproject.component.CustomDividerComponent
import com.lion.a02_boardcloneproject.component.CustomIconButton
import com.lion.wandertrip.R
import com.lion.wandertrip.component.CustomRatingBar
import com.lion.wandertrip.component.LottieLoadingIndicator
import com.lion.wandertrip.model.ReviewModel
import com.lion.wandertrip.presentation.detail_page.DetailViewModel
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun VerticalReviewList(detailViewModel: DetailViewModel) {
    val sh = detailViewModel.tripApplication.screenHeight
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height((sh).dp)
        ) {
            items(detailViewModel.filteredReviewList) {
                CustomDividerComponent(3.dp)
                Log.d("test100"," it : ${it.contentsDocId}")
                ReviewItem(
                    it,
                    detailViewModel,
                    pos = detailViewModel.filteredReviewList.indexOf(it)
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomDividerComponent(3.dp)
            }
        }
    }


@Composable
fun ReviewItem(reviewModel: ReviewModel, detailViewModel: DetailViewModel, pos: Int) {
    Log.d("test ReviewModel ","reviewModel.contentDocId: ${reviewModel.contentsDocId}")
    val sh = detailViewModel.tripApplication.screenHeight

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    ) {
        // 최상단 등록자 정보와 수정/삭제 버튼이 포함된 Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // 등록자 이미지
                GlideImage(
                    imageModel = reviewModel.reviewWriterProfileImgURl,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .clip(RoundedCornerShape(8.dp)),  // 이미지 둥글게 만들기
                    circularReveal = CircularReveal(duration = 250),
                    placeHolder = ImageBitmap.imageResource(R.drawable.img_image_holder),
                )
                Spacer(modifier = Modifier.width(8.dp))
                // 등록자 이름, 리뷰 개수
                Column {
                    Text(text = reviewModel.reviewWriterNickname, fontWeight = FontWeight.Bold)
                    Text(text = "20개의 리뷰", color = Color.Gray)
                }
            }

            // 수정 / 삭제 버튼 (우측 정렬)
            if(detailViewModel.tripApplication.loginUserModel.userNickName==reviewModel.reviewWriterNickname)
            Row {
                // 수정 버튼
                CustomIconButton(
                    ImageVector.vectorResource(R.drawable.ic_edit_24px),
                    iconButtonOnClick = {
                        detailViewModel.onClickIconReviewModify(
                            reviewModel.contentsDocId,
                            reviewModel.contentsId,
                            reviewModel.reviewDocId
                        )
                    })
                Spacer(modifier = Modifier.width(8.dp))
                // 삭제버튼
                CustomIconButton(
                    ImageVector.vectorResource(R.drawable.ic_delete_24px),
                    iconButtonOnClick = {
                        detailViewModel.deleteReview(contentDocId = reviewModel.contentsDocId, reviewModel.reviewDocId)
                    })
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 별점 및 여행 날짜
        Row(verticalAlignment = Alignment.CenterVertically) {
            CustomRatingBar(reviewModel.reviewRatingScore)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "${detailViewModel.convertToMonthDate(reviewModel.reviewTimeStamp)} 여행",
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 전체 리뷰 내용
        Text(
            text = reviewModel.reviewContent,
            modifier = Modifier.heightIn(100.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 이미지 가로 스크롤
        LazyRow {
            items(reviewModel.reviewImageList.size) { index ->
                GlideImage(
                    imageModel = reviewModel.reviewImageList[index],
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((sh / 10).dp)
                        .clip(RoundedCornerShape(8.dp)),
                    circularReveal = CircularReveal(duration = 250),
                    placeHolder = ImageBitmap.imageResource(R.drawable.img_image_holder),
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 좋아요 및 댓글 아이콘, 날짜 및 메뉴 아이콘
        Row(
            modifier = Modifier.fillMaxWidth(),
             horizontalArrangement = Arrangement.End
        ) {
            // 날짜
            Text(text = detailViewModel.convertToDate(reviewModel.reviewTimeStamp), color = Color.Gray)
        }
    }
}
