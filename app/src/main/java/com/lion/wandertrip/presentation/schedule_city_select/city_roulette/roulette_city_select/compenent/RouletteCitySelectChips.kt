package com.lion.wandertrip.presentation.schedule_city_select.city_roulette.roulette_city_select.compenent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lion.wandertrip.ui.theme.NanumSquareRoundRegular
import com.lion.wandertrip.util.AreaCode

@Composable
fun RouletteCitySelectChips(
    selectedCities: List<AreaCode>,
    onRemoveCity: (AreaCode) -> Unit
) {
    LazyRow( // ✅ LazyRow로 변경 (가로 스크롤 가능)
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(selectedCities) { city -> // ✅ items 사용
            AssistChip(
                onClick = {},
                enabled = false,
                label = {
                    Text(
                        city.areaName,
                        fontFamily = NanumSquareRoundRegular
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { onRemoveCity(city) },
                        modifier = Modifier.size(14.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "삭제",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                },
                colors = AssistChipDefaults.assistChipColors(
                    disabledContainerColor = Color(0xff556c9a), // ✅ 비활성화 시 배경색 유지
                    labelColor = Color.White, // ✅ 기본 글씨 색
                    disabledLabelColor = Color.White // 비활성화 시 글씨 색 유지
                ),
            )
        }
    }
}
