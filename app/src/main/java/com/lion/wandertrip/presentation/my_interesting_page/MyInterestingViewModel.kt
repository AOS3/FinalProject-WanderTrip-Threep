package com.lion.wandertrip.presentation.my_interesting_page

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lion.wandertrip.TripApplication
import com.lion.wandertrip.model.ContentsModel
import com.lion.wandertrip.model.UserInterestingModel
import com.lion.wandertrip.presentation.my_interesting_page.used_dummy_data.InterestingDummyData
import com.lion.wandertrip.service.ContentsService
import com.lion.wandertrip.service.TripCommonItemService
import com.lion.wandertrip.service.UserService
import com.lion.wandertrip.util.ContentTypeId
import com.lion.wandertrip.util.MainScreenName
import com.lion.wandertrip.util.Tools
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyInterestingViewModel @Inject constructor(
    @ApplicationContext context: Context,
    val userService: UserService,
    val tripCommonItemService: TripCommonItemService,
    val contentsService: ContentsService,
) : ViewModel() {
    val tripApplication = context as TripApplication

    // 유저 관심 목록 전체 전체지역
    val interestingListAll = mutableStateListOf<UserInterestingModel>()

    // 지역 필터된 유저 관심 목록 리스트
    val interestingListFilterByCity = mutableStateListOf<UserInterestingModel>()

    // 지역 리스트
    val localList = mutableStateListOf<String>()

    // 초기값은 0 -> 전체도시
    val filteredCityName = mutableStateOf("전체")

    // 관광지 클릭 됐는가?
    val isCheckAttraction = mutableStateOf(true)

    // 식당 클릭 됐는가?
    val isCheckRestaurant = mutableStateOf(true)

    // 숙소 클릭 됐는가?
    val isCheckAccommodation = mutableStateOf(true)

    // 바텀 시트 관리 상태 변수
    val isSheetOpen = mutableStateOf(false)

    // 로딩 상태 변수
    val isLoading = mutableStateOf(false)

    // 유저 좋아요 목록에 해당 페이지 있는가를 나타내는 상태변수
    val isLikeContentValue = mutableStateOf(false)

    // 리스트 좋아요 상태 저장하는 맵
    val likeMap = mutableStateMapOf<Int, Boolean>()

    // 하트 변경 메서드
    fun onClickIconHeart(contentId: String, pos: Int) {
        Log.d("test100", "contentId : contentId")
        onClickIconIsLikeContent(contentId, pos)
    }

    // 좋아요 목록에 해당 글이 있는가?
    fun isLikeContent(contentId: String) {
        viewModelScope.launch {
            val work1 = async(Dispatchers.IO) {
                userService.isLikeContent(tripApplication.loginUserModel.userDocId, contentId)
            }
            isLikeContentValue.value = work1.await()
        }
    }

    // 좋아요 버튼 누를때 리스너 메서드
    fun onClickIconIsLikeContent(contentId: String, pos: Int) {
        likeMap[pos] = !likeMap[pos]!!
        Log.d("onClickIconIsLikeContent", "map : $likeMap")
        Log.d("onClickIconIsLikeContent", "contentId : $contentId")
        viewModelScope.launch {
            // 좋아요상태를 변경한다.
            val work1 = async(Dispatchers.IO) {
                when (likeMap[pos]) {
                    true -> {
                        userService.addLikeItem(
                            tripApplication.loginUserModel.userDocId,
                            contentId
                        )
                        userService.addLikeCnt(contentId)
                    }

                    false -> {
                        userService.removeLikeItem(
                            tripApplication.loginUserModel.userDocId,
                            contentId
                        )

                        userService.removeLikeCnt(contentId)
                        // 전체 리스트에서도 삭제
                        interestingListAll.remove(interestingListFilterByCity[pos])
                        // 필터된 리스트에서도 삭제
                        interestingListFilterByCity.removeAt(pos)
                        // 새로 수정된 필터 리스트에서 맵 초기화
                        interestingListFilterByCity.forEachIndexed { index, userInterestingModel ->
                            likeMap[index] = true
                        }

                    }

                    null -> {
                        Log.d("onClickIconIsLikeContent", "null")
                    }
                }
            }
            work1.join()
            // 변경 상태를 화면에 반영한다
            isLikeContent(contentId)
        }

    }

    // 뒤로가기
    fun onClickNavIconBack() {
        tripApplication.navHostController.popBackStack()
    }

    fun setState(value : Boolean){
        isLoading.value= value
        Log.d("setState","setState : $value")
    }

    // 리스트 가져오기
    fun getInterestingList() {
        Log.d("test", "getInterestingList 메서드 진입")
        setState(true)
        viewModelScope.launch {
            // 내 유저에서, 좋아요 한 contentID 다 방아오기
            val work1 = async(Dispatchers.IO) {
                userService.gettingUserInterestingContentIdList(tripApplication.loginUserModel.userDocId)
            }
            val contentIdList = work1.await()
            // service 에서 contentId List 넘겨주고 interestingList 받아오기
            val work2 = async(Dispatchers.IO) {
                tripCommonItemService.gettingTripItemCommonInteresting(
                    contentIdList.toMutableList(),
                    null
                )
            }
            // api 로 가져온 목록들
            val userInterestingModelList = work2.await()


            // 컨텐츠 model을 가져와야 함
            userInterestingModelList.forEach {
                val work3 = async(Dispatchers.IO) {
                    contentsService.getContentByContentsId(it.contentID)
                }
                val contentModel = work3.await()
                it.ratingScore = contentModel.ratingScore
                it.starRatingCount = contentModel.getRatingCount
                it.saveCount = contentModel.interestingCount
            }

            /* userInterestingModelList.forEach {
                 Log.d("test100", "item : $it")
             }*/
            interestingListAll.addAll(userInterestingModelList)
            getInterestingFilter(filteredCityName.value)
            val work4 = async(Dispatchers.Main) {
                getLocalList()
            }
            work4.join()
            setState(false)
        }
    }


    // 필터로 리스트 가져오기
    fun getInterestingFilter(cityName: String) {
        Log.d("test100", "getInterestingFilterByCityList-> cityName : $cityName")
        Log.d("getInterestingFilter","filterList : ${interestingListFilterByCity}")
        when (cityName) {
            "전체" -> {
                // 필터 리스트 클리어
                interestingListFilterByCity.clear()
                interestingListFilterByCity.addAll(interestingListAll)
            }

            else -> {
                // 필터 리스트 클리어
                interestingListFilterByCity.clear()
                val filteredList = interestingListAll.filter {
                    val city = Tools.getAreaDetails(it.areacode, it.sigungucode)
                    city == cityName
                }
                Log.d("test100", "filteredList ${filteredList}")
                interestingListFilterByCity.addAll(
                    filteredList
                )
            }
        }

        // 관광지가 선택되지 않았다면
        if (!isCheckAttraction.value) {
            interestingListFilterByCity.removeIf {
                ContentTypeId.TOURIST_ATTRACTION.contentTypeCode.toString() == it.contentTypeID
            }
        }

        // 식당이 선택되지 않았다면
        if (!isCheckRestaurant.value) {
            interestingListFilterByCity.removeIf {
                ContentTypeId.RESTAURANT.contentTypeCode.toString() == it.contentTypeID
            }
        }

        // 숙소가 선택되지 않았다면
        if (!isCheckAccommodation.value) {
            interestingListFilterByCity.removeIf {
                ContentTypeId.ACCOMMODATION.contentTypeCode.toString() == it.contentTypeID
            }
        }
        // 맵 초기화
        likeMap.clear()
        // 맵 생성
        interestingListFilterByCity.forEachIndexed { index, userInterestingModel ->
            likeMap[index] = true
        }

    }

    // 관심 지역 가져오기
    fun getLocalList() {
        Log.d("test100", "interListAll $interestingListAll")
        localList.clear()
        localList.add("전체")
        interestingListAll.forEach {
            Log.d("test100", "interListItem $it")

            val cityName = Tools.getAreaDetails(it.areacode, it.sigungucode)
            if (!localList.contains(cityName)) {
                localList.add(cityName)
            }
        }
        Log.d("test100", "LocalList $localList")

    }

    // 관광지 클릭 리스너
    fun onClickButtonAttraction() {
        isCheckAttraction.value = !isCheckAttraction.value
        getInterestingFilter(filteredCityName.value)
    }

    // 식당 클릭 리스너
    fun onClickButtonRestaurant() {
        isCheckRestaurant.value = !isCheckRestaurant.value
        getInterestingFilter(filteredCityName.value)

    }

    // 숙소 클릭 리스너
    fun onClickButtonAccommodation() {
        isCheckAccommodation.value = !isCheckAccommodation.value
        getInterestingFilter(filteredCityName.value)

    }

    // 디테일 페이지로 이동
    fun onClickListItemToDetailScreen(contentId: String) {
        tripApplication.navHostController.navigate("${MainScreenName.MAIN_SCREEN_DETAIL}/$contentId")
    }


}