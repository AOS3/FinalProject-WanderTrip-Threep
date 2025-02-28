package com.lion.wandertrip.presentation.schedule_city_select.city_roulette.roulette_city_select.compenent

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Timestamp
import com.lion.wandertrip.ui.theme.NanumSquareRoundRegular
import com.lion.wandertrip.util.AreaCode

@Composable
fun RouletteCitySelectList(
    dataList : List<AreaCode>,
    selectedData : List<AreaCode>,
    onSelectedCity: (AreaCode) -> Unit
){
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(dataList.size) { index ->
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 지역 이미지
//                    Image(
//                        painter = painterResource(id = dataList[index].imageResId),
//                        contentDescription = dataList[index].areaName,
//                        modifier = Modifier
//                            .size(width = 100.dp, height = 50.dp) // 이미지 크기 조정
//                            .padding(end = 10.dp)
//                    )

                    // 지역 이름
                    Text(
                        text = dataList[index].areaName,
                        fontSize = 20.sp,
                        fontFamily = NanumSquareRoundRegular,
                        modifier = Modifier.weight(1f)
                    )

                    // 선택 버튼
                    OutlinedButton(
                        onClick = {

                            onSelectedCity(dataList[index])
                        },
                        enabled = !selectedData.contains(dataList[index]),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF435C8F),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .width(40.dp) // 🔹 버튼 너비 설정
                            .height(32.dp), // 🔹 버튼 높이 설정
                        contentPadding = PaddingValues(5.dp) // 패딩 제거 (빈 공간 없애기)
                    ) {
                        Text(
                            text = "추가",
                            fontSize = 12.sp,
                            fontFamily = NanumSquareRoundRegular,
                            modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center), // 가로 및 세로 중앙 정렬,
                        )
                    }
                }

                // CustomDividerComponent()
            }
        }

    }
}