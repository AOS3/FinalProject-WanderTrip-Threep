package com.lion.wandertrip.presentation.schedule_add.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lion.wandertrip.ui.theme.NanumSquareRoundLight
import com.lion.wandertrip.ui.theme.NanumSquareRoundRegular

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleAddTextInputLayout(
    label: String,
    placeholder: String = "",
    value: String,
    errorMessage: String? = null,
    onValueChange: (String) -> Unit
) {
    // 포커스 관리 객체 생성
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = if (errorMessage != null) Color.Red else Color.Gray, // ✅ 에러 시 테두리 빨간색
            unfocusedIndicatorColor = if (errorMessage != null) Color.Red else Color.Gray
        )
    )

    // ✅ 에러 메시지 표시
    if (errorMessage != null) {
        Text(
            text = errorMessage,
            fontFamily = NanumSquareRoundRegular,
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 20.dp)
        )
    }
}