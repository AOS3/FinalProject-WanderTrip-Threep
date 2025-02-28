package com.lion.wandertrip.presentation.google_map_page

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lion.wandertrip.TripApplication
import com.lion.wandertrip.model.ContentsModel
import com.lion.wandertrip.model.DetailModel
import com.lion.wandertrip.model.TripCommonItem
import com.lion.wandertrip.service.ContentsService
import com.lion.wandertrip.service.TripCommonItemService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoogleMapViewModel @Inject constructor(
    @ApplicationContext context: Context,
    val tripCommonItemService: TripCommonItemService,
    val contentsService: ContentsService
) : ViewModel() {
    val tripApplication = context as TripApplication

    val isLoading = mutableStateOf(false)

    // 권한 관리 변수
    val hasLocationPermission = mutableStateOf(false)

    /*    // tripCommon 관리 변수
        val tripCommonValue = mutableStateOf(TripCommonItem())

        // contentModel 관리 변수
        val contentValue = mutableStateOf(ContentsModel())*/

    // detailModel 관리 변수
    val detailValue = mutableStateOf(DetailModel())

    // 모델 가져오기
    fun getModels(contentId: String) {
        isLoading.value=true
        viewModelScope.launch {
            val work1 = async(Dispatchers.IO) {
                tripCommonItemService.getTripCommonItem(contentId = contentId, null)
            }
            // 커먼모델 받기
            val tripCommonModel = work1.await()!!
            val work2 = async(Dispatchers.IO) {
                contentsService.getContentByContentsId(contentId)
            }
            // 컨텐트 모델 받기
            val contentModel = work2.await()

            val filledDetailModel = DetailModel(
                contentID = contentId,
                detailTitle = tripCommonModel.title!!,
                detailAddress ="${tripCommonModel.addr1!!} ${tripCommonModel.addr2!!}",
                detailDescription ="${tripCommonModel.overview}",
                detailHomepage =tripCommonModel.homepage.toString(),
                detailRating =contentModel.ratingScore,
                detailLat =tripCommonModel.mapLat?.toDouble()?:0.0,
                detailLong =tripCommonModel.mapLng?.toDouble()?:0.0,
                detailImage =tripCommonModel.firstImage.toString(),
                detailPhoneNumber =tripCommonModel.tel!!,
                likeCnt = contentModel.interestingCount,
                ratingCount = contentModel.getRatingCount
            )
            detailValue.value = filledDetailModel
        }
        isLoading.value=false
    }


    // 뒤로가기
    fun onClickNavIconBack() {
        tripApplication.navHostController.popBackStack()
    }

    // 컨텐트 줄이기
    fun makeShortDescriptionText(str: String): String {
        return if (str.length > 30) {
            str.take(30) + "..."
        } else {
            str
        }
    }

    // 제목 줄이기
    fun makeShortTitleText(str: String): String {
        return if (str.length > 12) {
            str.take(30) + "..."
        } else {
            str
        }
    }

}