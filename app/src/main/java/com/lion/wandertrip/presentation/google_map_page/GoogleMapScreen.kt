package com.lion.wandertrip.presentation.google_map_page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.lion.a02_boardcloneproject.component.CustomIconButton
import com.lion.a02_boardcloneproject.component.CustomOutlinedButton
import com.lion.a02_boardcloneproject.component.CustomTopAppBar
import com.lion.wandertrip.R
import com.lion.wandertrip.component.CustomRatingBar
import com.lion.wandertrip.component.LottieLoadingIndicator
import com.lion.wandertrip.model.DetailModel
import com.lion.wandertrip.presentation.google_map_page.components.RowModelInfo
import com.lion.wandertrip.util.CustomFont
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

// 매개변수로 모델 넘겨받아야 함
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GoogleMapScreen(contentId: String, googleMapViewModel: GoogleMapViewModel = hiltViewModel()) {

    val sh = googleMapViewModel.tripApplication.screenHeight
    val sw = googleMapViewModel.tripApplication.screenWidth

    Log.d("googleMap", "contentId: $contentId")

    // 제목 줄이기 12 자 이상이면 ... 붙임
    val shortedTitle =
        googleMapViewModel.makeShortTitleText(googleMapViewModel.detailValue.value.detailTitle)

    // 위치 권한 체크
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    // 권한 요청을 확인하고 처리하는 부분
    // 컴포저블 전부 열린 후 딱 한번 실행
    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()
    }


    // 권한 요청을 확인하고 처리
    // allPermissionsGranted로 받아야 처리가 된다.
    LaunchedEffect(permissionState.allPermissionsGranted) {
        googleMapViewModel.hasLocationPermission.value = permissionState.allPermissionsGranted
        // 모델 가져온다.
        googleMapViewModel.getModels(contentId)
    }


    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = shortedTitle,
                navigationIconImage = Icons.AutoMirrored.Filled.ArrowBack,
                navigationIconOnClick = {
                    googleMapViewModel.onClickNavIconBack()
                }
            )
        },
        content = { paddingValues ->
            if (!googleMapViewModel.hasLocationPermission.value) {

                Column {
                    // 권한이 없을 때 메시지나 다른 UI를 표시할 수 있습니다.
                    Text("위치 권한을 허용해 주세요.", modifier = Modifier.padding(paddingValues))
                    CustomOutlinedButton(
                        text = "권한 수락하기",
                        onClick = {
                            permissionState.launchMultiplePermissionRequest()
                        },
                    )
                }

            } else {

                if (googleMapViewModel.detailValue.value.detailLat != 0.0) {

                    // 권한이 있을 경우 GoogleMap 표시
                    val contentModel = googleMapViewModel.detailValue.value
                    Log.d("gM", "lat : ${contentModel.detailLat}, lng : ${contentModel.detailLong}")
                    val lat = contentModel.detailLat
                    val long = contentModel.detailLong

                    val cameraPositionState = rememberCameraPositionState {
                        Log.d("gM", "latLng : ${LatLng(lat, long)}")

                        if (lat != 0.0 && long != 0.0)
                            position = CameraPosition.fromLatLngZoom(LatLng(lat, long), 14f)
                    }
                    val uiSettings = remember {
                        MapUiSettings(
                            myLocationButtonEnabled = true, // 내위치 버튼 보이기
                            scrollGesturesEnabled = true // 드래그로 카메라 움직임 제한 해제
                        )
                    }
                    // 내위치 찾기 버튼 기억하는 속성
                    // 구글 맵에 주입하면 내위치로 가기 버튼이 생성
                    val properties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = true)) }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        GoogleMap(
                            modifier = Modifier.matchParentSize(), // Box 크기와 동일하게 설정
                            cameraPositionState = cameraPositionState,
                            properties = properties,
                            uiSettings = uiSettings
                        ) {
                            Marker(state = MarkerState(position = LatLng(lat, long)))
                        }

                        // 하단에 지역 정보 표시
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height((sh / 12).dp)
                                .padding(16.dp)
                                .align(Alignment.BottomCenter)
                                .background(Color.White)
                                .clip(RoundedCornerShape(16.dp))
                        ) {
                            RowModelInfo(googleMapViewModel, contentModel)
                        }
                    }
                }
            }
        }
    )
}
