package com.lion.wandertrip.presentation.schedule_detail_friends

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.lion.wandertrip.presentation.schedule_detail_friends.component.AddFriendDialog
import com.lion.wandertrip.presentation.schedule_detail_friends.component.ScheduleDetailFriendsList
import com.lion.wandertrip.ui.theme.NanumSquareRound

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleDetailFriendsScreen(
    scheduleDocId: String,
    scheduleWriteNickName: String,
    viewModel: ScheduleDetailFriendsViewModel = hiltViewModel()
) {
    Log.d("ScheduleDetailFriendsScreen", "scheduleDocId: $scheduleDocId")

    // 일정 문서 ID 설정
    viewModel.setScheduleDocId(scheduleDocId)

    // 다이얼로그 표시 여부를 기억하는 상태
    var showAddFriendDialog by remember { viewModel.showAddFriendDialog }

    // 닉네임 값 상태
    var inviteNickname by remember { mutableStateOf("") }

    // 에러 메세지 상태값
    val addFriendError by viewModel.friendAddError

    LaunchedEffect(Unit) {
        viewModel.observeScheduleDocIdList()
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White // ✅ 투명색 적용
                ),
                title = {
                    Text(text = "함께하는 친구", fontFamily = NanumSquareRound) // ✅ 제목 설정
                },
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.backScreen() }
                    ) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "뒤로 가기")
                    }
                },
                actions = {
                    if (scheduleWriteNickName == viewModel.application.loginUserModel.userNickName) {
                        IconButton(
                            onClick = { showAddFriendDialog = true }
                        ) {
                            Icon(imageVector = Icons.Filled.PersonAdd, contentDescription = "친구 초대")
                        }
                    }
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            ScheduleDetailFriendsList(
                friends = viewModel.friendsUserList,
                scheduleWriteNickName = scheduleWriteNickName,
                viewModel = viewModel,
                profileImageUrl = "http://tong.visitkorea.or.kr/cms/resource/69/3383069_image2_1.JPG"
            )
            // 다이얼로그 표시
            if (showAddFriendDialog) {
                AddFriendDialog(
                    nickname = inviteNickname,
                    onNicknameChange = { inviteNickname = it },
                    onDismiss = {
                        viewModel.showAddFriendDialog.value = false
                        viewModel.friendAddError.value = ""
                    },
                    onConfirm = {
                        // 결과에 따라 ViewModel 내 상태가 업데이트됨
                        viewModel.addFriendByNickName(inviteNickname)
                        // nickname 초기화는 성공 시 ViewModel에서 처리됨
                    },
                    errorMessage = addFriendError
                )
            }
        }
    }

}