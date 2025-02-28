package com.lion.wandertrip.presentation.detail_review_write_page.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun CustomBasicTextField(
    placeholder: String,
    textFieldValue: MutableState<String>,
) {
    var isFocused by rememberSaveable { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() } // FocusRequester 생성

    val onValueChange: (String) -> Unit = { newValue ->
        textFieldValue.value = newValue // 상태를 업데이트
        Log.d("CustomTextField", "변경된 값: ${textFieldValue.value}") // 값 확인용 로그
    }
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            // 텍스트가 없고 포커스가 없을 때 플레이스홀더 표시
            if (!isFocused && textFieldValue.value.isEmpty()) {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .clickable {
                            focusRequester.requestFocus() // 클릭하면 포커스를 텍스트 필드로 이동
                        }
                )
            }

            // 텍스트 필드
            BasicTextField(
                value = textFieldValue.value,
                onValueChange = onValueChange,
                singleLine = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester) // FocusRequester 적용
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused // 포커스 상태 업데이트
                    }
                    .padding(16.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(lineHeight = 24.sp)
            )
        }
    }
}
