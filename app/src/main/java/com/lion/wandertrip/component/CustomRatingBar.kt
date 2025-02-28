package com.lion.wandertrip.component

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ColorFilter
import com.lion.wandertrip.R

@Composable
fun CustomRatingBar(rating: Float) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        // 별점의 수 (5개로 고정)
        val fullStars = rating.toInt() // 완전 채워진 별
        val decimal = rating - fullStars // 소수점 부분 (반별 처리)
        val hasHalfStar = decimal >= 0.75f // 반별 여부 (0.75 이상이면 반별을 꽉 채운 걸로 처리)
        val hasQuarterStar = decimal >= 0.25f && decimal < 0.75f // 0.25~0.74의 경우 반반만 채운 상태
        val emptyStars = 5 - fullStars - if (hasHalfStar || hasQuarterStar) 1 else 0 // 비어 있는 별

        // 완전 채워진 별
        repeat(fullStars) {
            Image(
                painter = painterResource(id = R.drawable.ic_star_full_24px), // 별 이미지
                contentDescription = "Full star",
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color(0xFFFFD700)) // 진한 금색 계열
            )

        }

        // 반별 (0.75 이상일 때)
        if (hasHalfStar) {
            Image(
                painter = painterResource(id = R.drawable.ic_half_star_24px), // 반별 이미지
                contentDescription = "Half star",
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color(0xFFFFD700)) // 진한 금색 계열

            )
        }
        // 0.25~0.74 사이일 때 반별
        else if (hasQuarterStar) {
            Image(
                painter = painterResource(id = R.drawable.ic_qurter_star_24px), // 1/4 채운 별 이미지
                contentDescription = "Quarter star",
                modifier = Modifier.size(26.dp),
                colorFilter = ColorFilter.tint(Color(0xFFFFD700)) // 진한 금색 계열

            )
        }

        // 비어있는 별
        repeat(emptyStars) {
            Image(
                painter = painterResource(id = R.drawable.ic_star_24px), // 비어있는 별 이미지
                contentDescription = "Empty star",
                modifier = Modifier.size(20.dp),
                colorFilter = ColorFilter.tint(Color(0xFFFFD700)) // 진한 금색 계열

            )
        }
    }
}
