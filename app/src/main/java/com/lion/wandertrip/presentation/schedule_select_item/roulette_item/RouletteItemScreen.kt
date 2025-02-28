package com.lion.wandertrip.presentation.schedule_select_item.roulette_item

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.Timestamp
import com.lion.a02_boardcloneproject.component.CustomTopAppBar
import com.lion.wandertrip.model.TripItemModel
import com.lion.wandertrip.presentation.schedule_select_item.ScheduleSelectItemViewModel
import com.lion.wandertrip.presentation.schedule_select_item.roulette_item.component.RoulettePointerForTripItems
import com.lion.wandertrip.presentation.schedule_select_item.roulette_item.component.RouletteWheelForTripItems
import com.lion.wandertrip.ui.theme.NanumSquareRoundRegular
import com.lion.wandertrip.util.ScheduleScreenName
import com.lion.wandertrip.util.SharedTripItemList
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun RouletteItemScreen(
    tripScheduleDocId : String,
    areaName: String,
    areaCode: Int,
    scheduleDate: Timestamp,
//    scheduleSelectItemViewModel: ScheduleSelectItemViewModel = hiltViewModel(
//        navController.getBackStackEntry(
//            "${ScheduleScreenName.SCHEDULE_SELECT_ITEM_SCREEN.name}?" +
//                    "itemCode={itemCode}&areaName={areaName}&areaCode={areaCode}&scheduleDate={scheduleDate}&tripScheduleDocId={tripScheduleDocId}"
//        )
//    ),
    viewModel: RouletteItemViewModel = hiltViewModel(),
) {
    // tripItemList 초기화
    LaunchedEffect(Unit) {
        viewModel.tripItemList.clear()
        viewModel.tripItemList.addAll(SharedTripItemList.sharedTripItemList)
        Log.d("RouletteItemScreen", "tripItemList 로드 완료: ${viewModel.tripItemList.size} 개 항목")
    }

    val coroutineScope = rememberCoroutineScope()
    val animatedRotation = remember { Animatable(0f) }
    var selectedItem by remember { mutableStateOf<TripItemModel?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    // rouletteItemList가 변경될 때마다 12시 방향(회전값 0)으로 초기화
    LaunchedEffect(SharedTripItemList.rouletteItemList) {
        animatedRotation.snapTo(0f)
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CustomTopAppBar(
                title = "룰렛 돌리기",
                navigationIconImage = Icons.Filled.ArrowBack,
                navigationIconOnClick = { viewModel.application.navHostController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(contentAlignment = Alignment.Center) {

                // 룰렛
                RouletteWheelForTripItems(
                    items = SharedTripItemList.rouletteItemList,
                    rotationAngle = animatedRotation.value
                )

                // 12시 방향에 고정된 포인터
                RoulettePointerForTripItems()
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Button(
                    onClick = { viewModel.moveToRouletteItemSelectScreen() },
                    shape = CircleShape,
                    modifier = Modifier.padding(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF435C8F)
                    ),
                    border = BorderStroke(1.dp, Color(0xFF435C8F))
                ) {
                    Text("여행지 추가하기", fontSize = 16.sp)
                }
                Button(
                    onClick = {
                        coroutineScope.launch {
                            // 랜덤 회전 값 (2~4회전 정도)
                            val randomRotation = Random.nextInt(720, 1440).toFloat()
                            animatedRotation.animateTo(
                                targetValue = animatedRotation.value + randomRotation,
                                animationSpec = tween(durationMillis = 2500, easing = FastOutSlowInEasing)
                            )

                            // 회전 후 현재 각도를 0~360 범위로 보정
                            val finalRotation = animatedRotation.value % 360
                            val itemCount = SharedTripItemList.rouletteItemList.size
                            val sliceAngle = if (itemCount > 0) 360f / itemCount else 360f
                            // 12시 방향(270도)을 기준으로 당첨 항목 계산
                            val selectedIndex = if (itemCount > 0)
                                (((270f - finalRotation + 360) % 360) / sliceAngle).toInt() % itemCount
                            else -1

                            if (selectedIndex >= 0) {
                                selectedItem = SharedTripItemList.rouletteItemList[selectedIndex]
                            }
                            showDialog = true
                        }
                    },
                    shape = CircleShape,
                    modifier = Modifier.padding(10.dp),
                    enabled = SharedTripItemList.rouletteItemList.isNotEmpty()
                ) {
                    Text("룰렛 돌리기", fontSize = 16.sp)
                }
            }

            if (showDialog && selectedItem != null) {
                // 당첨 후 다이얼로그
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
                                viewModel.addTripItemToSchedule(
                                    tripItemModel = selectedItem!!,
                                    tripScheduleDocId = tripScheduleDocId,
                                    areaName = areaName,
                                    areaCode = areaCode,
                                    scheduleDate = scheduleDate,
                                )
                            }
                        ) {
                            Text("결정 하기")
                        }
                    },
                    title = { Text("🎉 선택된 도시") },
                    text = { Text("당신의 여행지는 \"${selectedItem?.title}\" 입니다!") }
                )

            }
        }
    }
}

