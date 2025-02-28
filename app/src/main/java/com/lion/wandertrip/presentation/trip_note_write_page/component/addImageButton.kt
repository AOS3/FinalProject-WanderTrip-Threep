package com.lion.wandertrip.presentation.trip_note_write_page.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddImageButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(60.dp) // 정사각형 크기 설정
            .background(Color(0xFFD9D9D9), shape = RoundedCornerShape(12.dp)) // 회색 배경과 정사각형 모양
            .clickable(onClick = onClick), // 클릭 이벤트 처리
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Add, // "+" 아이콘
            contentDescription = "Add",
            tint = Color.Black // 아이콘 색상
        )
    }
}