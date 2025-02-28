package com.lion.wandertrip.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomBlueButton(
    text: String = "Button",
    paddingTop: Dp = 0.dp,
    paddingBottom: Dp = 0.dp,
    paddingStart: Dp = 0.dp,
    paddingEnd: Dp = 0.dp,
    buttonHeight: Dp? = null, // 기본값을 null로 설정하여, 지정되지 않으면 wrapContent처럼 동작하게
    buttonWidth: Dp? = null, // 버튼 너비 추가
    onClick: () -> Unit = {}
) {
    Button(
        modifier = Modifier
            .then(
                // 버튼 높이가 지정되었으면 적용, 없으면 wrapContent처럼 동작
                buttonHeight?.let { Modifier.height(it) } ?: Modifier
            )
            .then(
                // 버튼 너비가 지정되었으면 적용, 없으면 fillMaxWidth처럼 동작
                buttonWidth?.let { Modifier.width(it) } ?: Modifier.fillMaxWidth()
            )
            .padding(top = paddingTop, bottom = paddingBottom, start = paddingStart, end = paddingEnd),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp), // 모서리를 둥글게 만듦
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF435C8F), // 버튼 배경색
            contentColor = Color.White // 텍스트 색상
        ),
    ) {
        Text(text = text)
    }
}