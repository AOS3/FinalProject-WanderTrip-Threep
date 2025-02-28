package com.lion.wandertrip.presentation.schedule_item_review

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.lion.wandertrip.R
import com.lion.wandertrip.component.LottieLoadingIndicator
import com.lion.wandertrip.ui.theme.NanumSquareRound
import com.lion.wandertrip.ui.theme.NanumSquareRoundRegular

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleItemReviewScreen(
    tripScheduleDocId: String,
    scheduleItemDocId: String,
    scheduleItemTitle: String,
    viewModel: ScheduleItemReviewViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current // ← 키보드 제어용

    // 리뷰 텍스트
    var reviewText by remember { mutableStateOf("") }

    // 🔹 저장 중인지 확인
    val isLoading by viewModel.isLoading

    // 화면 진입 시 데이터 가져 오기
    LaunchedEffect(Unit) {
        viewModel.getScheduleItemByDocId(tripScheduleDocId, scheduleItemDocId)
    }

    // scheduleItem.value.itemReviewText가 변경될 때마다 reviewText도 갱신
    LaunchedEffect(viewModel.scheduleItem.value.itemReviewText) {
        reviewText = viewModel.scheduleItem.value.itemReviewText
    }

    // 앨범에서 이미지 선택
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            // 단순히 ViewModel에 전달 (즉시 업로드 X)
            viewModel.onImagePicked(context, uri)
        }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                ),
                title = {
                    Text(
                        text = "후기 작성",
                        fontFamily = NanumSquareRound,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.backScreen() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "뒤로 가기")
                    }
                },
                actions = {
                    // "저장" 버튼 -> newBitmaps 업로드 + DB 저장
                    IconButton(
                        onClick = {
                            // 키보드 내리기
                            focusManager.clearFocus()

                            viewModel.saveReview(tripScheduleDocId, scheduleItemDocId, reviewText)
                        }
                    ) {
                        Icon(imageVector = Icons.Filled.Save, contentDescription = "저장")
                    }
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // 상단에 선택된 여행지 제목
            Text(
                text = scheduleItemTitle,
                fontFamily = NanumSquareRound,
                fontSize = 25.sp
            )
            Spacer(modifier = Modifier.height(15.dp))

            // 1) 이미 DB에 저장된 사진들 표시
            val oldImages = viewModel.scheduleItem.value.itemReviewImagesURL

            // 2) 새로 추가된 (아직 업로드 전) 이미지들 표시
            val newImages = viewModel.newBitmaps

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()) // 가로 스크롤 허용 (이미지가 많을 때)
            ) {
                // 1) 기존 이미지들
                oldImages.forEachIndexed { index, imageUrl ->
                    Box(
                        modifier = Modifier
                            .size(width = 90.dp, height = 70.dp)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = imageUrl),
                            contentDescription = "기존 이미지",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        IconButton(
                            onClick = {
                                viewModel.removeImageFromOld(index)
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "사진 삭제",
                                tint = Color.White
                            )
                        }
                    }
                }

                // 2) 새로 추가된 (아직 업로드 전) 이미지들
                newImages.forEachIndexed { index, bitmap ->
                    Box(
                        modifier = Modifier
                            .size(width = 90.dp, height = 70.dp)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = bitmap),
                            contentDescription = "새 이미지",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        IconButton(
                            onClick = {
                                viewModel.removeImageFromNew(index)
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "사진 삭제",
                                tint = Color.White
                            )
                        }
                    }
                }

                // 3) ‘+’ 버튼 (최대 3장 제한)
                val totalImageCount = oldImages.size + newImages.size
                if (totalImageCount < 3) {
                    IconButton(
                        onClick = {
                            pickImageLauncher.launch("image/*")
                        },
                        modifier = Modifier
                            .size(width = 90.dp, height = 70.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFECECEC))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "사진 추가",
                            tint = Color.Gray
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            // 후기 작성 영역
            OutlinedTextField(
                value = reviewText,
                onValueChange = { text ->
                    reviewText = text
                },
                label = {
                    Text(
                        text = "후기",
                        fontFamily = NanumSquareRoundRegular,
                    )
                },
                // 조건부 placeholder
                placeholder = {
                    // 포커스 아니고 텍스트도 비어있을 때만 placeholder 표시
                    Text("후기를 작성하세요.")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
    // 🔹 저장 중일 때 Lottie 로딩 오버레이
    if (isLoading) {
        LottieLoadingIndicator()
    }
}
