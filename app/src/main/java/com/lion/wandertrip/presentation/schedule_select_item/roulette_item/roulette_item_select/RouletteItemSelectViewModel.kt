package com.lion.wandertrip.presentation.schedule_select_item.roulette_item.roulette_item_select

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.lion.wandertrip.TripApplication
import com.lion.wandertrip.model.TripItemModel
import com.lion.wandertrip.service.TripScheduleService
import com.lion.wandertrip.service.UserService
import com.lion.wandertrip.util.ScheduleScreenName
import com.lion.wandertrip.util.SharedTripItemList
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouletteItemSelectViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    val tripScheduleService: TripScheduleService,
    val userService: UserService
) : ViewModel() {

    val application = context as TripApplication

    // 관심 지역 목록
    val userLikeList = mutableStateListOf<String>()

    // 선택된 여행지 리스트 업데이트
    fun updateRouletteItemList(selectedItems: List<TripItemModel>) {
        SharedTripItemList.rouletteItemList.clear()
        SharedTripItemList.rouletteItemList.addAll(selectedItems)
    }

    // 유저 관심 지역 옵저브
    fun observeUserLikeList() {
        val firestore = FirebaseFirestore.getInstance()
        val userDocId = application.loginUserModel.userDocId
        val userLikeCollectionRef = firestore.collection("UserData")
            .document(userDocId)
            .collection("UserLikeList")

        userLikeCollectionRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("observeUserLikeList", "Error: ${error.message}")
                return@addSnapshotListener
            }
            if (snapshot != null) {
                // 각 문서의 "contentId" 값을 추출합니다.
                val likeItems = snapshot.documents.mapNotNull { doc ->
                    doc.getString("contentId")
                }
                // 기존 리스트를 클리어하고 최신 값으로 업데이트합니다.
                userLikeList.clear()
                userLikeList.addAll(likeItems)
                Log.d("observeUserLikeList", "userLikeList updated: $userLikeList")
            }
        }
    }

    // 관심 지역 추가
    fun addLikeItem(likeItemContentId: String) {
        viewModelScope.launch {
            val work1 = async(Dispatchers.IO) {
                userService.addLikeItem(application.loginUserModel.userDocId, likeItemContentId)
            }.await()
        }
    }

    // 관심 지역 삭제
    fun removeLikeItem(likeItemContentId: String) {
        viewModelScope.launch {
            val work1 = async(Dispatchers.IO) {
                userService.removeLikeItem(application.loginUserModel.userDocId, likeItemContentId)
            }.await()
        }
    }

}