package com.lion.wandertrip

import android.app.Application
import androidx.navigation.NavHostController
import com.lion.wandertrip.model.UserModel
import dagger.hilt.android.HiltAndroidApp

// Hilt를 사용하려면 이 어노테이션을 애플리케이션의 Application 클래스에 선언해야 합니다.
@HiltAndroidApp
class TripApplication : Application() {
    // 로그인한 사용자 객체
    lateinit var loginUserModel: UserModel
    // 네비게이션
    lateinit var navHostController: NavHostController
}