package com.lion.wandertrip.presentation.main_page

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.lion.wandertrip.presentation.bottom_menu_page.BottomMenuScreen

// 컨트롤러 받아서 쓰지말고, viewModel 만들고 viewModel 에서 아래처럼 써야 할 거같아요 일단 컨트롤러 받는거 두겠습니다.

// @HiltViewModel
//class UserLoginViewModel @Inject constructor(
//    @ApplicationContext context: Context,
//    val userService: UserService,
//) : ViewModel()

@Composable
fun MainScreen() {
    BottomMenuScreen()
}