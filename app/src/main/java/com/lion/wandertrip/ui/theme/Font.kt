package com.lion.wandertrip.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.lion.wandertrip.R // 🔹 프로젝트 패키지에 맞게 변경

// 🔹 Nanum Square Round 폰트 패밀리 정의
val NanumSquareRound = FontFamily(
    Font(R.font.nanum_square_roun_bold, FontWeight.Bold) // res/font/nanum_square_roun_bold.ttf 연결
)

val NanumSquareRoundRegular = FontFamily(
    Font(R.font.nanum_square_roun_regular, FontWeight.Thin)
)

val NanumSquareRoundLight = FontFamily(
    Font(R.font.nanum_square_roun_light, FontWeight.Light)
)

