package com.lion.wandertrip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.lion.wandertrip.ui.theme.WanderTripTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WanderTripTheme {
                TripMain()
            }
        }
    }
}




@Composable
fun TripMain() {

    // 충돌해라
    // 네비게이션 객체
    // Composable 함수가 재구성(recompose)되더라도 NavController의 인스턴스가 유지됨 (즉, 화면 회전이나 재구성이 발생해도 동일한 인스턴스를 사용).
    val rememberNavHostController = rememberNavController()
    // Appilcation 객체에 담는다.
    // Jetpack Compose에서 LocalContext.current는 현재 Composable 함수가 실행되는 컨텍스트(Context)를 가져오는 방법입니다.
    // applicationContext의 기본 타입은 Context이므로, 우리가 만든 TripApplication 클래스의 인스턴스로 사용하려면 형변환이 필요합니다.
    val tripApplication = LocalContext.current.applicationContext as TripApplication
    tripApplication.navHostController = rememberNavHostController

}



