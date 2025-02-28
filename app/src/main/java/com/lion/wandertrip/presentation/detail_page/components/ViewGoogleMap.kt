package com.lion.wandertrip.presentation.detail_page.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.lion.wandertrip.presentation.detail_page.DetailViewModel
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ViewGoogleMap(detailViewModel: DetailViewModel) {
    val sh = detailViewModel.tripApplication.screenHeight
/*    val hasLocationPermission = remember { mutableStateOf(false) }

    // 위치 권한 체크
    // rememberMultiplePermissionsState  = 리스트에 있는 모든권한이 허용돼있을때 true 상태
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    // 권한 요청을 확인하고 처리
    LaunchedEffect(permissionState) {
        if (permissionState.allPermissionsGranted) {
            hasLocationPermission.value = true
        } else {
            hasLocationPermission.value = false
        }
    }*/

  /*  // 권한이 없으면 지도를 표시하지 않음
    if (!hasLocationPermission.value) {
        // 권한이 없을 때 메시지나 다른 UI를 표시할 수 있습니다.
        Text("위치 권한을 허용해 주세요.")
    } else {*/
        // 권한이 있을 경우 GoogleMap 표시

    // 권한을 사용하지 않아서 위 코드 전부 주석처리함 내위치 사용하지않아 권한 필요없음
        val contentModel = detailViewModel.tripCommonContentModelValue.value
        val lat = contentModel.mapLat
        val long = contentModel.mapLng

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(lat!!.toDouble(), long!!.toDouble()), 14f)
        }
        val uiSettings = remember {
            MapUiSettings(
                myLocationButtonEnabled = false,
                scrollGesturesEnabled = false // 드래그로 카메라 움직임 제한
            )
        }
        val properties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = false)) } // 여기서 내현재 위치를 딴다, 따라서 권한 필요

        Box(modifier = Modifier.fillMaxSize().height((sh/9).dp)) {
            GoogleMap(
                modifier = Modifier.matchParentSize(), // Box 크기와 동일하게 설정
                cameraPositionState = cameraPositionState,
                properties = properties,
                uiSettings = uiSettings
            ) {
                Marker(state = MarkerState(position = LatLng(lat!!.toDouble(), long!!.toDouble())))
            }
        }
    //}
}