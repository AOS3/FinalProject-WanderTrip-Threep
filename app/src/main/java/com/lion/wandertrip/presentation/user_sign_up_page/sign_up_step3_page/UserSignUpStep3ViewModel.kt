package com.lion.wandertrip.presentation.user_sign_up_page.sign_up_step3_page

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.lion.wandertrip.TripApplication
import com.lion.wandertrip.model.UserModel
import com.lion.wandertrip.service.UserService
import com.lion.wandertrip.util.MainScreenName
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSignUpStep3ViewModel @Inject constructor(
    @ApplicationContext context: Context,
    val userService: UserService
) :ViewModel() {
    val tripApplication = context as TripApplication

    val textFieldUserJoinStep1NickNameValue = mutableStateOf("")

    val textFieldUserJoinStep1NickNameErrorText = mutableStateOf("")

    val textFieldUserJoinStep1NickNameIsError = mutableStateOf(false)

    val textFieldUserJoinStep1NickNameReadOnly = mutableStateOf(false)

    // 닉네임 중복확인을 성공했는지
    var isCheckUserNickName = mutableStateOf(false)

    // 닉네임 중복확인 메서드
    // 닉네임 중복확인을 누르면 호출되는 함수
    fun onClickButtonCheckUserNickName() {
        // 사용자가 입력한 아이디
        val userNickName = textFieldUserJoinStep1NickNameValue.value

        // 입력한 것이 없다면
        if (userNickName.isEmpty()) {
            textFieldUserJoinStep1NickNameIsError.value = true
            textFieldUserJoinStep1NickNameErrorText.value = "닉네임을 입력해주세요"
            return
        }
        // 사용할 수 있는 닉네임인지 검사한다.
        CoroutineScope(Dispatchers.Main).launch {
            val work1 = async(Dispatchers.IO) {
                userService.checkJoinUserNickName(userNickName)
            }
            isCheckUserNickName.value = work1.await()
            if (isCheckUserNickName.value) {
                textFieldUserJoinStep1NickNameIsError.value = false
                textFieldUserJoinStep1NickNameErrorText.value = "사용 가능한 닉네임 입니다"
                textFieldUserJoinStep1NickNameReadOnly.value = true
            } else {
                textFieldUserJoinStep1NickNameIsError.value = true
                textFieldUserJoinStep1NickNameErrorText.value = "이미 존재하는 닉네임입니다."
            }
        }

    }

    // 다음 버튼을 누르면 호출되는 메서드
    // 회원가입 메서드
    fun onClickKaKaoButtonSignUp(kakaoToken:Long){
        // 기본에 있는 에러 메시지는 모두 초기화
        textFieldUserJoinStep1NickNameErrorText.value=""
        textFieldUserJoinStep1NickNameIsError.value=false

        var isError = false

        if(!isCheckUserNickName.value){
            textFieldUserJoinStep1NickNameErrorText.value = "닉네임 중복 확인을 해주세요"
            textFieldUserJoinStep1NickNameIsError.value = true
            isError = true
        }

        if(isError == false) {
            // TODO 유저 정보 등록
            val userModel = UserModel(
                userNickName = textFieldUserJoinStep1NickNameValue.value,
                kakaoId = kakaoToken
            )

            CoroutineScope(Dispatchers.Main).launch {
                // 비동기 작업으로 유저 정보 추가 후 userDocId 받아오기
                val work1 = async(Dispatchers.IO) {
                    userService.addUserData(userModel)
                }

                // userDocId 값을 받음
                val userDocId = work1.await()
                // 어플리케이션에 userModel 저장
                userModel.userDocId = userDocId
                tripApplication.loginUserModel = userModel

                // SharedPreference에 저장한다.
                val pref = tripApplication.getSharedPreferences("KakaoToken", Context.MODE_PRIVATE)
                pref.edit {
                    putString("kToken", kakaoToken.toString())
                    Log.d("userSingStep3","ktoken: $kakaoToken")
                }

           /*     // Preference에 login token이 있는지 확인한다.
                val kakaoPref = tripApplication.getSharedPreferences("KakaoToken", Context.MODE_PRIVATE)
                val kToken = kakaoPref.getString("kToken",null)
                Log.d("userSingStep3","토큰 가져오기 : $kToken")*/

                // 프로필사진 설정 화면으로 이동, userDocId 값을 경로에 포함시켜 전달
                tripApplication.navHostController.navigate("${MainScreenName.MAIN_SCREEN_USER_SIGN_UP_STEP2.name}/${"KakaoLogin"}/$userDocId")
            }
        }
    }



    //뒤로가기 메서드
    fun navigationIconOnClick() {
        tripApplication.navHostController.popBackStack()
    }
}