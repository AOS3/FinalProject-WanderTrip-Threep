package com.lion.wandertrip.component

import android.content.res.ColorStateList
import android.widget.RatingBar
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
internal fun CustomDraggableRatingBar(
    ratingState: MutableState<Float>, // 별점 상태를 관리할 수 있도록 MutableState로 선언
    isSmall: Boolean = false, // 작은 사이즈의 RatingBar 여부
    changAble: Boolean = true, // 별점이 변경 가능한지 여부
    progressTintColor: Color? = Color.Yellow, // RatingBar의 색상 변경
    onRatingChanged: (Float) -> Unit // 별점이 변경될 때 호출되는 함수
) {
    // AndroidView로 기존 RatingBar를 Compose UI에 추가
    AndroidView(
        factory = { context ->
            // RatingBar의 크기 설정
            if (isSmall) {
                RatingBar(context, null, android.R.attr.ratingBarStyleSmall).apply {
                    // 별점 상태 초기화
                    this.rating = ratingState.value
                    setIsIndicator(!changAble) // changable이 false일 경우, RatingBar가 수정 불가능하게 설정

                    // 색상 변경이 필요한 경우 설정
                    progressTintColor?.let {
                        progressTintList = ColorStateList(
                            arrayOf(
                                intArrayOf(android.R.attr.state_enabled), // 활성화된 상태
                            ), intArrayOf(
                                it.hashCode(),
                            )
                        )

                        secondaryProgressTintList = ColorStateList(
                            arrayOf(
                                intArrayOf(android.R.attr.state_enabled), // 활성화된 상태
                            ), intArrayOf(
                                it.hashCode(),
                            )
                        )
                    }

                    // 별점 변경 리스너 설정
                    setOnRatingBarChangeListener { _, rating, _ ->
                        ratingState.value = rating // 별점 값 변경 시 상태 갱신
                        onRatingChanged(rating) // 외부 콜백 함수 호출
                    }
                }
            } else {
                RatingBar(context).apply {
                    // 별점 상태 초기화
                    this.rating = ratingState.value
                    setIsIndicator(!changAble) // changable이 false일 경우, RatingBar가 수정 불가능하게 설정

                    // 색상 변경이 필요한 경우 설정
                    progressTintColor?.let {
                        progressTintList = ColorStateList(
                            arrayOf(
                                intArrayOf(android.R.attr.state_enabled), // 활성화된 상태
                            ), intArrayOf(
                                it.hashCode(),
                            )
                        )

                        secondaryProgressTintList = ColorStateList(
                            arrayOf(
                                intArrayOf(android.R.attr.state_enabled), // 활성화된 상태
                            ), intArrayOf(
                                it.hashCode(),
                            )
                        )
                    }

                    // 별점 변경 리스너 설정
                    setOnRatingBarChangeListener { _, rating, _ ->
                        ratingState.value = rating // 별점 값 변경 시 상태 갱신
                        onRatingChanged(rating) // 외부 콜백 함수 호출
                    }
                }
            }
        },
        update = { view ->
            // View가 갱신될 때 별점 상태를 업데이트
            view.rating = ratingState.value // 최신 상태로 RatingBar의 별점 갱신
        }
    )
}
