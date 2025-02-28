package com.lion.wandertrip.presentation.schedule_add.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleAddCalendar(
    startDate: MutableState<Timestamp>,
    endDate: MutableState<Timestamp>,
    onDateRangeSelected: (Timestamp, Timestamp) -> Unit // ✅ 날짜 선택 이벤트 추가
) {
    val dateRangePickerState = rememberDateRangePickerState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp)
            .height(350.dp)
    ) {
        // ✅ DateRangePicker (화면에 항상 표시)
        DateRangePicker(
            title = null,
            state = dateRangePickerState,
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.Black,
                ),
            headline = null,
            colors = DatePickerDefaults.colors(containerColor = Color.White) // ✅ 내부 배경도 흰색으로 변경
        )

        // ✅ 날짜 선택 로직
        val startMillis = dateRangePickerState.selectedStartDateMillis
        var endMillis = dateRangePickerState.selectedEndDateMillis

        // ✅ 만약 종료 날짜가 선택되지 않았으면, 시작 날짜를 자동으로 종료 날짜로 설정
        if (startMillis != null && endMillis == null) {
            endMillis = startMillis
        }

        if (startMillis != null && endMillis != null) {
            val startTimestamp = Timestamp(startMillis / 1000, 0) // ✅ 밀리초 → 초 변환
            val endTimestamp = Timestamp(endMillis / 1000, 0)

            // ✅ 시작 날짜를 업데이트
            startDate.value = startTimestamp
            endDate.value = endTimestamp

            // ✅ 날짜 두 개가 선택되었을 때 이벤트 실행
            onDateRangeSelected(startTimestamp, endTimestamp)
        }
    }
}
