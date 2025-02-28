package com.lion.wandertrip.presentation.user_sign_up_page.sign_up_step3_page

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lion.a02_boardcloneproject.component.CustomOutlinedTextField
import com.lion.a02_boardcloneproject.component.CustomTopAppBar
import com.lion.a02_boardcloneproject.component.LikeLionOutlinedTextFieldEndIconMode
import com.lion.a02_boardcloneproject.component.LikeLionOutlinedTextFieldInputType
import com.lion.wandertrip.component.BlueButton

// 카카오 로그인으로 가입시
@Composable
fun UserSignUpStep3Screen(
    kakaoToken: String,
    userSignUpStep3ViewModel: UserSignUpStep3ViewModel = hiltViewModel()
) {
    Log.d("test","UserSignUpStep3Screen -> kakaoTk $kakaoToken")
    Scaffold(
        containerColor = Color.White,
        topBar = {
            CustomTopAppBar(
                title = "카카오 회원 가입",
                navigationIconImage = Icons.AutoMirrored.Filled.ArrowBack,
                navigationIconOnClick = {
                    userSignUpStep3ViewModel.navigationIconOnClick()
                },
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(it).padding(start = 30.dp, end = 30.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Column(
            ) {

                // 닉네임 요소
                CustomOutlinedTextField(
                    textFieldValue = userSignUpStep3ViewModel.textFieldUserJoinStep1NickNameValue,
                    label = "닉네임",
                    placeHolder = "닉네임을 입력해주세요",
                    inputCondition = "[^가-힣a-zA-Z0-9_]",
                    singleLine = true,
                    paddingTop = 10.dp,
                    inputType = LikeLionOutlinedTextFieldInputType.TEXT,
                    supportText = userSignUpStep3ViewModel.textFieldUserJoinStep1NickNameErrorText,
                    isError = userSignUpStep3ViewModel.textFieldUserJoinStep1NickNameIsError,
                    readOnly = userSignUpStep3ViewModel.textFieldUserJoinStep1NickNameReadOnly.value
                )


                // 닉네임 중복 확인 버튼
                BlueButton(
                    text = "닉네임 중복 확인",
                    paddingTop = 10.dp,
                    onClick = {
                        userSignUpStep3ViewModel.onClickButtonCheckUserNickName()
                    }
                )
            }
            // 회원가입 완료
            BlueButton(
                text = "회원가입 완료",
                paddingTop = 10.dp,
                onClick = {
                    userSignUpStep3ViewModel.onClickKaKaoButtonSignUp(kakaoToken = kakaoToken.toLong())
                }
            )
        }
    }

}