package com.lion.wandertrip.presentation.trip_note_other_schedule_page

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.lion.wandertrip.R
import com.lion.wandertrip.presentation.trip_note_other_schedule_page.component.TripNoteOtherScheduleItemList
import com.lion.wandertrip.ui.theme.NanumSquareRound


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripNoteOtherScheduleScreen(
    otherNickName : String,
    tripNoteOtherScheduleViewModel: TripNoteOtherScheduleViewModel = hiltViewModel(),
) {

    tripNoteOtherScheduleViewModel.gettingTripNoteScheduleData(otherNickName)
    tripNoteOtherScheduleViewModel.gettingTripNoteProfileData(otherNickName)
    tripNoteOtherScheduleViewModel.showImageUri.value

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "여행기 목록",
                        fontFamily = NanumSquareRound,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(end = 5.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { tripNoteOtherScheduleViewModel.navigationButtonClick() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White, // 배경색을 흰색으로 설정
                    titleContentColor = Color.Black, // 제목 텍스트 색상 설정 (필요 시 변경)
                    navigationIconContentColor = Color.Black // 네비게이션 아이콘 색상 설정 (필요 시 변경)
                )
            )
        },

        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally, // 가로 정렬 중앙
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Spacer(modifier = Modifier.height(130.dp)) // 상단 여백


                val painter = if (tripNoteOtherScheduleViewModel.showImageUri.value != null) {
                    rememberImagePainter(tripNoteOtherScheduleViewModel.showImageUri.value)
                } else {
                    painterResource(id = R.drawable.ic_person_24px)
                }

                Image(
                    painter = painter,
                    contentDescription = "프로필 이미지",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape),
                    contentScale = ContentScale.Crop
                )



                Spacer(modifier = Modifier.height(14.dp)) // 이미지와 텍스트 사이의 여백

                // 텍스트 (중앙 정렬)
                Text(
                    text = "${otherNickName}님",
                    fontFamily = NanumSquareRound,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 22.dp)
                )

//                // 일정 목록 표시
//                if (tripNoteOtherScheduleViewModel.tripNoteOtherScheduleList.isNotEmpty()) {
//                    TripNoteOtherScheduleItemList(
//                        dataList = tripNoteOtherScheduleViewModel.tripNoteOtherScheduleList,
//                        viewModel = tripNoteOtherScheduleViewModel,
//                        onRowClick = { position ->
//                            val documentId = tripNoteOtherScheduleViewModel.tripNoteOtherScheduleDocIdList.toList()[position]
//                            // Log를 이용해서 전달되는 값을 출력
//                            Log.d("TripNote", "Position: $position, DocumentId: $documentId")
//                            if (documentId != null) {
//                                tripNoteOtherScheduleViewModel.goTripNoteDocId(documentId)
//                            }
//                        },
//                        modifier = Modifier.padding(paddingValues)
//                    )
//                }

                if (tripNoteOtherScheduleViewModel.tripNoteOtherScheduleList.isNotEmpty()) {
                    TripNoteOtherScheduleItemList(
                        dataList = tripNoteOtherScheduleViewModel.tripNoteOtherScheduleList,
                        viewModel = tripNoteOtherScheduleViewModel,
                        onRowClick = { documentId ->  // String을 전달받음
                            Log.d("TripNote", "DocumentId: $documentId")
                            tripNoteOtherScheduleViewModel.goTripNoteDocId(documentId)  // documentId를 사용하여 상세 페이지로 이동
                        },
                        modifier = Modifier.padding(paddingValues)
                    )
                }

            }
        }
    )
}
