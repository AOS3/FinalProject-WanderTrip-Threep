package com.lion.wandertrip.presentation.detail_review_write_page

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lion.wandertrip.TripApplication
import com.lion.wandertrip.model.ContentsModel
import com.lion.wandertrip.model.ReviewModel
import com.lion.wandertrip.service.ContentsReviewService
import com.lion.wandertrip.service.ContentsService
import com.lion.wandertrip.service.UserService
import com.lion.wandertrip.util.Tools
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailReviewWriteViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val contentsReviewService: ContentsReviewService,
    val contentsService: ContentsService,
    val userService: UserService,
) : ViewModel() {
    val tripApplication = context as TripApplication

    // 별점 점수 상태 관리 변수
    val ratingScoreValue = mutableStateOf(5.0f)

    // 리뷰 내용 상태 변수
    val reviewContentValue = mutableStateOf("")

    // 이미지 가져 왔는지 상태 여부
    val isImagePicked = mutableStateOf(false)

    // 비트맵 리스트 상태 변수
    val mutableBitMapList = mutableStateListOf<Bitmap?>()

    // 로딩 변수
    val isLoading = mutableStateOf(false)


    // 뒤로가기
    fun onClickNavIconBack() {
        tripApplication.navHostController.popBackStack()
    }

    // 리뷰 올리는 메서드
    fun addContentsReview(contentId: String, title : String) {
        Log.d("test100"," title $title")
        viewModelScope.launch {

            val imagePathList = mutableListOf<String>()
            val serverFilePathList = mutableListOf<String>()
            var contentsDocId = ""
            var imageUrlList = listOf<String>()


            if (isImagePicked.value) {

                // 외장 메모리에 bitmap 저장
                mutableBitMapList.forEachIndexed { index, bitmap ->
                    val name = "image_${index}_${System.currentTimeMillis()}.jpg"
                    serverFilePathList.add(name)

                    val savedFilePath = Tools.saveBitmaps(tripApplication, bitmap!!, name)

                    imagePathList.add(savedFilePath)
                }
            }

            if (isImagePicked.value) {
                val work1 = async(Dispatchers.IO) {
                    uploadImage(imagePathList, serverFilePathList, contentId)
                }
                imageUrlList = work1.await()
            } else {
                Log.d("addContentsReview", "이미지 선택 안 됨, 업로드 스킵")
            }

            contentsDocId = contentsService.isContentExists(contentId)


            //  업로드가 끝난 후 리뷰 데이터 저장

            val review = ReviewModel().apply {
                reviewTitle = title
                contentsId = contentId
                reviewContent = reviewContentValue.value
                reviewImageList = imageUrlList // ✅ 업로드 완료 후 URL 리스트 저장
                reviewRatingScore = ratingScoreValue.value
                reviewWriterNickname = tripApplication.loginUserModel.userNickName
                reviewWriterProfileImgURl =
                    userService.gettingImage(tripApplication.loginUserModel.userProfileImageURL)
                        .toString()
            }

            if (contentsDocId.isNotEmpty()) {
                Log.d("addContentsReview", "기존 콘텐츠 문서 있음 - 리뷰 추가 중")
                contentsReviewService.addContentsReview(contentId, review)
            } else {
                Log.d("addContentsReview", "기존 콘텐츠 문서 없음 - 새 문서 생성 후 리뷰 추가 중")
                val contents = ContentsModel(contentId = contentId)
                contentsDocId = contentsService.addContents(contents)
                contentsReviewService.addContentsReview(contentId, review)
            }
            // 컨텐츠의 별점 부분을 업데이트 한다.
            val work2 = async(Dispatchers.IO) {
                addReviewAndUpdateContents(contentsDocId)
            }
            work2.join()

            tripApplication.navHostController.popBackStack()
            isLoading.value=false
        }
    }


    // url 리스트 리턴받는 메서드
    suspend fun uploadImage(
        sourceFilePath: List<String>,
        serverFilePath: List<String>,
        contentId: String
    ): List<String> {
        Log.d("uploadImage", "sourceFilePath: $sourceFilePath")
        Log.d("uploadImage", "serverFilePath: $serverFilePath")
        Log.d("uploadImage", "contentId: $contentId")

        // 📌 동기적으로 업로드 실행 후 결과 반환
        val resultUrlList = contentsReviewService.uploadReviewImageList(
            sourceFilePath,
            serverFilePath.toMutableList(), // `toMutableStateList()` 제거 (필요 없음)
            contentId
        )

        Log.d("uploadImage", "업로드된 이미지 URL 리스트: $resultUrlList")

        return resultUrlList ?: emptyList() // 업로드 실패 시 빈 리스트 반환
    }


    // 컨텐츠 의 별점 필드 수정
    suspend fun addReviewAndUpdateContents(contentDocId:String) {
        contentsService.updateContentRatingAndRatingCount(contentDocId)
    }


}