package com.lion.a02_boardcloneproject.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DividerComponent(
    paddingTop:Dp = 10.dp,
    color:Color = Color.LightGray)
{
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth().padding(top = paddingTop),
        color = color
    )
}
//abcdefg