package com.lion.wandertrip.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@Composable
fun ShimmerImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    // Coil을 이용해 네트워크 이미지를 로드하는 Painter 생성
    val painter = rememberAsyncImagePainter(model = imageUrl)
    val painterState = painter.state

    // 겹쳐서 그리기 위해 Box 사용
    Box(modifier) {
        // 1) 로딩 중이면 ShimmerPlaceholder 표시
        if (painterState is AsyncImagePainter.State.Loading) {
            Log.d("ShimmerImage", "ShimmerImage: Loading")
            ShimmerPlaceholder(
                modifier = Modifier
                    .matchParentSize()
            )
        }
        // 2) 실제 이미지
        Image(
            painter = painter,
            contentDescription = "Review Image",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
    }
}