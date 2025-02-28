package com.lion.wandertrip.presentation.schedule_city_select.city_roulette

import android.graphics.Paint
import android.graphics.Typeface
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.Timestamp
import com.lion.a02_boardcloneproject.component.CustomTopAppBar
import com.lion.wandertrip.presentation.schedule_city_select.ScheduleCitySelectViewModel
import com.lion.wandertrip.presentation.schedule_city_select.city_roulette.component.RoulettePointer
import com.lion.wandertrip.presentation.schedule_city_select.city_roulette.component.RouletteWheel
import com.lion.wandertrip.ui.theme.NanumSquareRoundRegular
import com.lion.wandertrip.util.RouletteScreenName
import com.lion.wandertrip.util.ScheduleScreenName
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random


@Composable
fun RouletteCityScreen(
    scheduleTitle: String,
    scheduleStartDate: Timestamp,
    scheduleEndDate: Timestamp,
    viewModel: RouletteCityViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        Log.d("RouletteCityScreen", "스케줄 제목 : $scheduleTitle")
        Log.d("RouletteCityScreen", "스케줄 시작 날짜 : $scheduleStartDate")
        Log.d("RouletteCityScreen", "스케줄 종료 날짜 : $scheduleEndDate")
    }

    val coroutineScope = rememberCoroutineScope()
    val animatedRotation = remember { Animatable(0f) } // ✅ 초기에는 항상 `0f`로 고정

    // 당첨된 도시
    var selectedCity by remember { mutableStateOf("") }
    // 다이얼 로그 표시 유무
    var showDialog by remember { mutableStateOf(false) }
    // 룰렛 항목
    var cities by remember { mutableStateOf(viewModel.cities) }

    // ✅ 항목 개수가 변경될 때마다 룰렛 위치 초기화 (무조건 12시 방향 고정)
    LaunchedEffect(viewModel.cities) {
        cities = viewModel.cities
        animatedRotation.snapTo(0f) // ✅ 무조건 초기화
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CustomTopAppBar(
                title = "룰렛 돌리기",
                navigationIconImage = Icons.Filled.ArrowBack,
                navigationIconOnClick = {
                    viewModel.application.navHostController.popBackStack()
                },
            )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(contentAlignment = Alignment.Center) {
                RouletteWheel(
                    items = cities,
                    rotationAngle = animatedRotation.value
                )
                RoulettePointer() // 🔴 화살표 (맨 위, 12시 방향)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Button(
                    onClick = {
                        // 룰렛의 도시 항목 추가 화면 으로 이동
                        viewModel.moveToRouletteCitySelectScreen()
                    },
                    shape = CircleShape,
                    modifier = Modifier.padding(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White, // ✅ 버튼 배경색: 흰색
                        contentColor = Color(0xFF435C8F) // ✅ 버튼 텍스트 색상: 파란색 (변경 가능)
                    ),
                    border = BorderStroke(1.dp, Color(0xFF435C8F)) // ✅ 테두리 설정
                ) {
                    Text(
                        text = "항목 추가하기",
                        fontFamily = NanumSquareRoundRegular,
                        color = Color(0xFF435C8F)
                    )
                }

                Button(
                    onClick = {
                        coroutineScope.launch {
                            val randomRotation = Random.nextInt(720, 1440).toFloat()
                            animatedRotation.animateTo(
                                targetValue = animatedRotation.value + randomRotation,
                                animationSpec = tween(durationMillis = 2500, easing = FastOutSlowInEasing)
                            )

                            // 🔹 정확한 당첨 도시 계산 (무조건 12시 방향에서 결정)
                            val finalRotation = animatedRotation.value % 360
                            val sliceAngle = if (cities.isNotEmpty()) 360f / cities.size else 360f

                            // ✅ `270도`를 기준으로 항상 맨 위가 당첨되도록 보정
                            val selectedCityIndex = if (cities.isNotEmpty()) {
                                (((270f - finalRotation + 360) % 360) / sliceAngle).toInt() % cities.size
                            } else -1

                            selectedCity = if (selectedCityIndex >= 0) cities[selectedCityIndex].areaName else "항목 없음"
                            showDialog = true
                        }
                    },
                    shape = CircleShape,
                    modifier = Modifier.padding(10.dp),
                    enabled = cities.isNotEmpty()
                ) {
                    Text(
                        text = "룰렛 돌리기",
                        fontFamily = NanumSquareRoundRegular
                    )
                }
            }

            // 🔹 선택된 도시 결과 다이얼로그
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                showDialog = false
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White, // ✅ 버튼 배경색: 흰색
                                contentColor = Color(0xFF435C8F) // ✅ 버튼 텍스트 색상: 파란색 (변경 가능)
                            ),
                            border = BorderStroke(1.dp, Color(0xFF435C8F)) // ✅ 테두리 설정
                        ) {
                            Text(
                                text = "다시 하기",
                                fontFamily = NanumSquareRoundRegular,
                                color = Color(0xFF435C8F)
                            )
                        }
                        Button(
                            onClick = {
                                showDialog = false
                                // 일정 상세 화면 으로 이동
                                viewModel.addTripSchedule(
                                    scheduleTitle = scheduleTitle,
                                    scheduleStartDate = scheduleStartDate,
                                    scheduleEndDate = scheduleEndDate,
                                    areaName = selectedCity,
                                )
                            }
                        ) {
                            Text("결정 하기")
                        }
                    },
                    title = { Text("🎉 선택된 도시") },
                    text = { Text("당신의 여행지는 \"$selectedCity\" 입니다!") }
                )
            }
        }
    }
}
