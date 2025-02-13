package com.lion.wandertrip.vo

import com.google.firebase.Timestamp
import com.lion.wandertrip.model.UserModel

class UserVO {
    var userDocId: String = ""
    var userId: String = ""
    var userPw: String = ""
    var userNickName: String = ""
    var userProfileImageURL: String = ""
    var userAutoLoginToken: String = ""
    var userLikeList: List<String> = emptyList()
    var rouletteAgainCnt: Int = 0
    var userTimeStamp: Timestamp = Timestamp.now()
    var userState: Int = 1

    fun toUserModel(): UserModel {
        val userModel = UserModel()
        userModel.userDocId = userDocId
        userModel.userId = userId
        userModel.userPw = userPw
        userModel.userNickName = userNickName
        userModel.userProfileImageURL = userProfileImageURL
        userModel.userAutoLoginToken = userAutoLoginToken
        userModel.userLikeList = userLikeList
        userModel.rouletteAgainCnt = rouletteAgainCnt
        userModel.userTimeStamp = userTimeStamp
        userModel.userState = userState
        return userModel
    }
}
////t2