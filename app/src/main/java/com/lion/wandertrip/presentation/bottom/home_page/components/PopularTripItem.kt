package com.lion.wandertrip.presentation.bottom.home_page.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.lion.wandertrip.R
import com.lion.wandertrip.model.TripNoteModel
import com.lion.wandertrip.ui.theme.NanumSquareRound

@Composable
fun PopularTripItem(
    tripItem: TripNoteModel,
    imageUrl: String?,
    onItemClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = tripItem.tripNoteTitle,
                    fontSize = 18.sp,
                    fontFamily = NanumSquareRound,
                )
            }

            when {
                imageUrl == "" -> { // 🔥 로딩 중 상태
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                imageUrl != null -> { // 🔥 Firebase Storage에서 가져온 URL 표시
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = tripItem.tripNoteTitle,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                else -> { // 🔥 이미지가 없을 경우 기본 이미지 표시
                    Image(
                        painter = painterResource(id = R.drawable.ic_hide_image_144dp),
                        contentDescription = "이미지 없음",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = tripItem.tripNoteContent,
                    fontSize = 18.sp,
                    fontFamily = NanumSquareRound,
                )
            }
        }
    }
}
