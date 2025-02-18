package com.lion.wandertrip.presentation.schedule_detail_page.component

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ScheduleDetailDropDawn(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onDelete: () -> Unit = {},
    onReview: () -> Unit = {},
    onMove: () -> Unit = {}
) {

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        DropdownMenuItem(
            text = { Text("삭제") },
            onClick = {
                // 삭제 기능 구현
                onDismissRequest()
            }
        )
        DropdownMenuItem(
            text = { Text("후기") },
            onClick = {
                // 후기 기능 구현
                onDismissRequest()
            }
        )
        DropdownMenuItem(
            text = { Text("위치조정") },
            onClick = {
                // 위치조정 기능 구현
                onDismissRequest()
            }
        )
    }
}