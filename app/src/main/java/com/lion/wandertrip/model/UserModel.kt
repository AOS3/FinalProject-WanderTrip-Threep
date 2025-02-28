package com.lion.wandertrip.model

import com.google.firebase.Timestamp
import com.lion.wandertrip.vo.UserVO

class UserModel(
    var userDocId: String = "",                     // 유저 문서 ID
    var userId: String = "",                        // 유저 ID
    var userPw: String = "",                        // 유저 비밀번호
    var userNickName: String = "",                  // 유저 닉네임
    var userProfileImageURL: String = "",           // 유저 프로필 이미지 URL
    var userAutoLoginToken: String = "",            // 자동 로그인 토큰
    var userLikeList: List<String> = emptyList(),   // 좋아요 누른 장소 content ID 목록
    var rouletteAgainCnt: Int = 0,                  // 룰렛 다시 돌린 횟수
    var userTimeStamp: Timestamp = Timestamp.now(), // 데이터 입력 시간 (Firebase Timestamp)
    var userState: Int = 1,                         // 유저 상태 (1: 정상, 2: 탈퇴)
    var userKakaoToken : String = "",               // 카카오토큰
    var userScheduleList : List<String> = emptyList(), // 유저가 만든 일정 리스트
    var invitedScheduleList : List<String> = emptyList(), // 초대 받은 일정 리스트
    var kakaoId : Long = 0
) {
    fun toUserVO(): UserVO {
        val userVO = UserVO()
        userVO.userDocId = userDocId
        userVO.userId = userId
        userVO.userPw = userPw
        userVO.userNickName = userNickName
        userVO.userProfileImageURL = userProfileImageURL
        userVO.userAutoLoginToken = userAutoLoginToken
        userVO.userLikeList = userLikeList
        userVO.rouletteAgainCnt = rouletteAgainCnt
        userVO.userTimeStamp = userTimeStamp
        userVO.userState = userState
        userVO.userKakaoToken=userKakaoToken
        userVO.userScheduleList = userScheduleList
        userVO.invitedScheduleList = invitedScheduleList
        userVO.kakaoId=kakaoId
        return userVO
    }
}
