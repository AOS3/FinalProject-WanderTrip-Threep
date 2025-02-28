package com.lion.wandertrip.presentation.schedule_city_select.city_roulette.component

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.lion.wandertrip.util.AreaCode
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RouletteWheel(
    items: List<AreaCode>,
    rotationAngle: Float
) {
    val sliceAngle = if (items.isNotEmpty()) 360f / items.size else 360f
    val radius = with(LocalDensity.current) { 150.dp.toPx() }

    val colors = listOf(
        Color(0xFFFFC107), Color(0xFFFF5722), Color(0xFFE91E63), Color(0xFF3F51B5),
        Color(0xFF009688), Color(0xFF8BC34A), Color(0xFFFF9800), Color(0xFF673AB7)
    )

    Canvas(
        modifier = Modifier
            .size(320.dp)
            .pointerInput(Unit) { detectTapGestures { } }
    ) {
        if (items.isEmpty()) {
            drawArc(
                color = Color.LightGray, // 🔹 항목이 없을 때 단일 색상 유지
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = true
            )
        } else {
            items.forEachIndexed { index, city ->
                val startAngle = index * sliceAngle + rotationAngle
                val midAngle = startAngle + (sliceAngle / 2)

                rotate(startAngle) {
                    drawArc(
                        color = colors[index % colors.size],
                        startAngle = 0f,
                        sweepAngle = sliceAngle,
                        useCenter = true
                    )
                }

                val textRadius = radius * 0.7f
                val textX = center.x + textRadius * cos(Math.toRadians(midAngle.toDouble())).toFloat()
                val textY = center.y + textRadius * sin(Math.toRadians(midAngle.toDouble())).toFloat()

                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        city.areaName,
                        textX,
                        textY,
                        Paint().apply {
                            color = android.graphics.Color.WHITE
                            textSize = 45f
                            textAlign = Paint.Align.CENTER
                            typeface = Typeface.DEFAULT_BOLD
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun RoulettePointer() {
    Canvas(
        modifier = Modifier
            .size(320.dp)
    ) {
        val pointerWidth = 80f  // 🔹 화살표 너비
        val pointerHeight = 120f // 🔹 화살표 높이 (룰렛을 살짝 넘어가도록)
        val cornerRadius = 15f // 🔹 모서리를 둥글게 만드는 반경

        val centerX = size.width / 2
        val centerY = 0f // 🔹 룰렛 중심 상단에 위치

        // 🔻 예쁜 디자인을 위한 좌표 설정
        val pointerTop = Offset(centerX, centerY + pointerHeight * 0.6f) // 🔹 좀 더 길게 돌출
        val pointerLeft = Offset(centerX - pointerWidth / 2, centerY - pointerHeight * 0.4f)
        val pointerRight = Offset(centerX + pointerWidth / 2, centerY - pointerHeight * 0.4f)

        val pointerPath = Path().apply {
            moveTo(pointerTop.x, pointerTop.y) // 🔻 중앙 하단
            lineTo(pointerRight.x, pointerRight.y) // 🔹 오른쪽
            arcTo(
                rect = Rect(
                    topLeft = Offset(pointerRight.x - cornerRadius, pointerRight.y - cornerRadius),
                    bottomRight = Offset(pointerLeft.x + cornerRadius, pointerLeft.y + cornerRadius)
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 180f,
                forceMoveTo = false
            ) // 🔹 하단을 둥글게
            lineTo(pointerLeft.x, pointerLeft.y) // 🔹 왼쪽
            close()
        }

        // 🔴 화살표 본체
        drawPath(
            path = pointerPath,
            color = Color.Red,
            style = Fill
        )

        // 🔵 테두리 효과 추가 (더 예쁘게 보이도록)
        drawPath(
            path = pointerPath,
            color = Color.Black,
            style = Stroke(width = 5f) // 🔹 테두리 두께 조정
        )
    }
}
