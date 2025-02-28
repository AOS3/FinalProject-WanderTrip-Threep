package com.lion.wandertrip.presentation.detail_page.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lion.wandertrip.util.CustomFont

@Composable
fun DetailColumnIconAndText(icon: ImageVector, text: String, onClick: () -> Unit = {}, isHeartBool :Boolean = false) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        val tint = when(isHeartBool){
            true -> Color.Red
            false -> Color.Black
        }
        Icon(imageVector = icon, contentDescription = text, modifier = Modifier.size(24.dp),

            tint = tint )
        Text(
            text, fontSize = 12.sp, fontFamily = CustomFont.customFontBold
        )
    }
}