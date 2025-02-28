package com.lion.wandertrip.presentation.user_sign_up_page.sign_up_step2_page

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lion.a02_boardcloneproject.component.CustomIconButton
import com.lion.a02_boardcloneproject.component.CustomTopAppBar
import com.lion.wandertrip.R
import com.lion.wandertrip.TripApplication
import com.lion.wandertrip.component.BlueButton
import com.lion.wandertrip.util.Tools
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun UserSignUpStep2Screen(
    fromWhere : String,
    userDocId: String,
    userSignUpStep2ViewModel: UserSignUpStep2ViewModel = hiltViewModel()
) {
    Log.d("test100","fromWhere : ${fromWhere}, userDocId : ${userDocId}")
    userSignUpStep2ViewModel.settingUserDocId(userDocId)
    userSignUpStep2ViewModel.gettingUserModelByUserDocId()

    // 촬영된 사진의 uri를 담을 객체
    lateinit var contentUri: Uri

    val context = LocalContext.current

    // 사진 촬영용 런처
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        if(it){
            Tools.takePictureData(context, contentUri, userSignUpStep2ViewModel.imageBitmapState)
            userSignUpStep2ViewModel.isImagePicked.value=true
        }
    }

    // 앨범용 런처
    val albumLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        Tools.takeAlbumData(context, it, userSignUpStep2ViewModel.imageBitmapState)
        if(it != null){
            userSignUpStep2ViewModel.isImagePicked.value=true
        }
    }

    val sh = userSignUpStep2ViewModel.tripApplication.screenHeight
    Scaffold(
        containerColor = Color.White,
        topBar = {
            CustomTopAppBar(
                menuItems = {
                    if(userSignUpStep2ViewModel.isImagePicked.value)
                    CustomIconButton(
                        icon = ImageVector.vectorResource(R.drawable.ic_check_24px),
                        iconButtonOnClick = {
                            // 저장 메서드
                            // 다음 화면으로 넘어감
                            userSignUpStep2ViewModel.onClickIconCheck(fromWhere)
                        }
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.SpaceBetween, // 위, 아래로 배치 균형 조정
            horizontalAlignment = Alignment.CenterHorizontally // 가로 정렬 중앙
        ) {
            Spacer(modifier = Modifier.height(40.dp)) // 상단 여백

            // 프로필 사진 선택 안내 텍스트
            Text(
                text = "프로필 사진 고르기",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            if(!userSignUpStep2ViewModel.isImagePicked.value){
                GlideImage(
                    imageModel = R.drawable.img_basic_profile_image,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((sh/7).dp)
                        .clip(RoundedCornerShape(60)),  // 이미지 둥글게 만들기
                    circularReveal = CircularReveal(duration = 250),
                    placeHolder = ImageBitmap.imageResource(R.drawable.img_basic_profile_image),
                )
            }else{
                GlideImage(
                    imageModel = userSignUpStep2ViewModel.imageBitmapState.value,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((sh/7).dp)
                        .clip(RoundedCornerShape(60)),  // 이미지 둥글게 만들기
                    circularReveal = CircularReveal(duration = 250),
                    placeHolder = ImageBitmap.imageResource(R.drawable.img_basic_profile_image),
                )
            }


            // 버튼 섹션
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp), // 버튼 간격 조정
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BlueButton(text = "카메라로 촬영") {
                    // 카메라 촬영 동작 추가
                    // 사진이 저정될 uri 객체를 가져온다.
                    contentUri = Tools.gettingPictureUri(context)
                    // 런처를 가동한다.
                    cameraLauncher.launch(contentUri)
                }

                BlueButton(text = "갤러리에서 가져오기") {
                    // 갤러리에서 이미지 선택 동작 추가
                    val pickVisualMediaRequest = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    albumLauncher.launch(pickVisualMediaRequest)
                }

                BlueButton(text = "건너뛰기") {
                    // 건너뛰기 동작 추가
                    userSignUpStep2ViewModel.onClickButtonPass(fromWhere)
                }
            }

            Spacer(modifier = Modifier.height(40.dp)) // 하단 여백
        }
    }
}
