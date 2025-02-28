package com.lion.wandertrip.presentation.my_interesting_page.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lion.wandertrip.presentation.my_interesting_page.MyInterestingViewModel
import com.lion.wandertrip.util.Tools

@Composable
fun CityDropdownButton(myInterestingViewModel: MyInterestingViewModel, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16), // 사각형 모양을 둥글게 설정
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // 배경색을 투명으로 설정
        border = BorderStroke(1.dp, Color.Gray), // 테두리 추가
        modifier = Modifier
            .height(50.dp), // 버튼 높이 설정
        contentPadding = PaddingValues(horizontal = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = myInterestingViewModel.filteredCityName.value, fontSize = 15.sp, color = Color.Black)
            Spacer(modifier = Modifier.width(8.dp)) // 텍스트와 아이콘 간격 조정
            Icon(
                imageVector = Icons.Default.ArrowDropDown, // 아래 방향 화살표 아이콘
                contentDescription = "Dropdown Arrow",
                tint = Color.Black
            )
        }
    }
}
