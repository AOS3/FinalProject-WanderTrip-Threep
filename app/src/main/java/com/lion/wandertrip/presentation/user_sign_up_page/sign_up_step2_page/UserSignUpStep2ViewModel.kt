package com.lion.wandertrip.presentation.user_sign_up_page.sign_up_step2_page

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.lion.wandertrip.TripApplication
import com.lion.wandertrip.model.UserModel
import com.lion.wandertrip.service.UserService
import com.lion.wandertrip.util.BotNavScreenName
import com.lion.wandertrip.util.MainScreenName
import com.lion.wandertrip.util.Tools
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserSignUpStep2ViewModel @Inject constructor(
    @ApplicationContext context: Context,
    val userService: UserService,
) : ViewModel() {
    // context
    val tripApplication = context as TripApplication

    // 유저 DocID 상태 변수
    val userDocIdState = mutableStateOf("")

    // 카메라나 앨범을 통해 가져온 사진을 담을 상태변수
    val imageBitmapState = mutableStateOf<Bitmap?>(null)

    // 앨범이나 사진에서 이미지를 받아왔는지 상태 관리 변수
    val isImagePicked = mutableStateOf(false)
   lateinit var userModel :UserModel

    // 체크 아이콘 누를 때 리스너
    fun onClickIconCheck(fromWhere:String) {
        CoroutineScope(Dispatchers.Main).launch {
            // 서버상에서의 파일 이름
            userModel.userProfileImageURL = "image_${System.currentTimeMillis()}.jpg"
            // 외부저장소에 ImageView에 있는 이미지 데이터를 저장한다.
            Tools.saveBitmap(tripApplication,  imageBitmapState.value!!)

            val work1 = async(Dispatchers.IO){
                val filePath = tripApplication.getExternalFilesDir(null).toString()
                userService.uploadImage("${filePath}/uploadTemp.jpg", userModel.userProfileImageURL)
            }
            work1.join()

            val work2 = async(Dispatchers.IO){
                userService.updateUserData(userModel)
            }
            work2.join()
            tripApplication.loginUserModel = userModel

        }
        if(fromWhere=="normal"){
            tripApplication.navHostController.popBackStack(MainScreenName.MAIN_SCREEN_USER_LOGIN.name, false)

        }else{
            //백스택 싹 지우기
            tripApplication.navHostController.popBackStack(MainScreenName.MAIN_SCREEN_USER_SIGN_UP_STEP2.name,true)
            // 카카오 로그인 시 홈으로 바로 이동
            tripApplication.navHostController.navigate(BotNavScreenName.BOT_NAV_SCREEN_HOME.name)
        }
    }

    // 건너뛰기 버튼
    fun onClickButtonPass(fromWhere: String) {
        if(fromWhere=="normal"){
            // 카카오 로그인 시 로그인 화면으로 바로 이동
            tripApplication.navHostController.navigate(MainScreenName.MAIN_SCREEN_USER_LOGIN.name) {
                // 이미 로그인 화면이 백스택에 있으면 중복 생성 방지
                launchSingleTop = true
            }
        }else{
            // 카카오 로그인 시 홈으로 바로 이동
            tripApplication.navHostController.navigate(BotNavScreenName.BOT_NAV_SCREEN_HOME.name){
                launchSingleTop=true
            }

        }
    }

    // 유저 DocId 가져오기
    fun settingUserDocId(userDocId:String) {
        userDocIdState.value = userDocId
    }

    // userModel 가져오기
    fun gettingUserModelByUserDocId() {
        Log.d("gettingUserModelByUserDocId","uStep2")
        CoroutineScope(Dispatchers.Main).launch {
            val work1= async(Dispatchers.IO){
                userModel = userService.getUserByUserDocId(userDocIdState.value)
            }
            Log.d("gettingUserModelByUserDocId","uStep2ENd")

            work1.join()
        }

    }
}