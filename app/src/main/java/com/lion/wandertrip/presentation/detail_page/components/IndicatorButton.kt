package com.lion.wandertrip.presentation.detail_page.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lion.wandertrip.ui.theme.wanderBlueColor
import com.lion.wandertrip.util.CustomFont

@Composable
fun IndicatorButton(onClick: () -> Unit, text: String, icon: ImageVector, mutableState : MutableState<Boolean>) {
    if(mutableState.value){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(8.dp)
        ) {
            Text(text, fontSize = 14.sp, fontFamily = CustomFont.customFontBold, color = wanderBlueColor)
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = wanderBlueColor // 원하는 색상 적용
            )

        }
    }else{
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(8.dp)
        ) {
            Text(text, fontSize = 14.sp, fontFamily = CustomFont.customFontBold, color = Color.Gray)
        }
    }

}