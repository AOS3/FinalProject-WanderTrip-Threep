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
    var userKakaoToken : String = ""
    var userScheduleList : List<String> = emptyList()  // 유저가 만든 일정 리스트
    var invitedScheduleList : List<String> = emptyList() // 초대 받은 일정 리스트
    var kakaoId : Long = 0


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
        userModel.userKakaoToken = userKakaoToken
        userModel.userScheduleList = userScheduleList
        userModel.invitedScheduleList = invitedScheduleList
        userModel.kakaoId=kakaoId
        return userModel
    }
}