package com.lion.wandertrip.service

import android.content.Context
import androidx.core.content.edit
import com.lion.wandertrip.model.UserModel
import com.lion.wandertrip.repository.UserRepository
import com.lion.wandertrip.util.LoginResult
import com.lion.wandertrip.util.UserState
import com.lion.wandertrip.vo.UserVO


class UserService (val userRepository: UserRepository) {

    // 가입하려는 아이디가 존재하는지 확인하는 메서드
    suspend fun checkJoinUserId(userId:String) : Boolean{
        // 아이디를 통해 사용자 정보를 가져온다.
        val userVoList = userRepository.selectUserDataByUserId(userId)
        // 가져온 데이터가 있다면
        if(userVoList.isNotEmpty()){
            return false
        }
        // 가져온 데이터가 없다면
        else {
            return true
        }
    }

    // 사용하려는 닉네임이 존재하는지 확인하는 메서드
    suspend fun checkJoinUserNickName(userNickName:String) : Boolean{
        // 닉네임을 통해 사용자 정보를 가져온다.
        val userVoList = userRepository.selectUserDataByUserNickName(userNickName)
        // 가져온 데이터가 있다면
        if(userVoList.isNotEmpty()){
            return false
        }
        // 가져온 데이터가 없다면
        else {
            return true
        }
    }

    // 사용자 정보를 추가하는 메서드
    fun addUserData(userModel: UserModel){
        // 데이터를 VO에 담아준다.
        val userVO = userModel.toUserVO()
        // 저장하는 메서드를 호출한다.
        userRepository.addUserData(userVO)
    }

    // 로그인 처리 메서드
    suspend fun checkLogin(loginUserId:String, loginUserPw:String) : LoginResult {
        // 로그인 결과
        var result = LoginResult.LOGIN_RESULT_SUCCESS

        // 입력한 아이디로 사용자 정보를 가져온다.
        val userVoList = userRepository.selectUserDataByUserId(loginUserId)

        // 가져온 사용자 정보가 없다면
        if(userVoList.isEmpty()){
            result = LoginResult.LOGIN_RESULT_ID_NOT_EXIST
        } else {
            if(loginUserPw != userVoList[0].userPw){
                // 비밀번호가 다르다면
                result = LoginResult.LOGIN_RESULT_PASSWORD_INCORRECT
            }
            // 탈퇴한 회원이라면
            if(userVoList[0].userState == UserState.USER_STATE_SIGN_OUT.number){
                result = LoginResult.LOGIN_RESULT_SIGN_OUT_MEMBER
            }
        }
        return result
    }

    // 사용자 아이디를 통해 문서 id와 사용자 정보를 가져온다.
    // 사용자 아이디와 동일한 사용자의 정보 하나를 반환하는 메서드
    suspend fun selectUserDataByUserIdOne(userId:String) : UserModel{
        val tempMap = userRepository.selectUserDataByUserIdOne(userId)

        val loginUserVo = tempMap["user_vo"] as UserVO
        val loginUserModel = loginUserVo.toUserModel()

        return loginUserModel
    }

    // 자동로그인 토큰값을 갱신하는 메서드
    suspend fun updateUserAutoLoginToken(context: Context, userDocumentId:String){
        // 새로운 토큰값을 발행한다.
        val newToken = "${userDocumentId}${System.nanoTime()}"
        // SharedPreference에 저장한다.
        val pref = context.getSharedPreferences("LoginToken", Context.MODE_PRIVATE)
        pref.edit {
            putString("token", newToken)
        }
        // 서버에 저장한다.
        userRepository.updateUserAutoLoginToken(userDocumentId, newToken)
    }
    // 자동 로그인 토큰 값으로 사용자 정보를 가져오는 메서드
    suspend fun selectUserDataByLoginToken(loginToken:String) : UserModel?{
        val loginMap = userRepository.selectUserDataByLoginToken(loginToken)
        if(loginMap == null){
            return null
        } else {
            val userVO = loginMap["userVO"] as UserVO

            val userModel = userVO.toUserModel()
            return userModel
        }
    }

    // 사용자 데이터를 수정한다.
    suspend fun updateUserData(userModel: UserModel){
        val userVO = userModel.toUserVO()
        userRepository.updateUserData(userVO)
    }

    // 사용자의 상태를 변경하는 메서드
    suspend fun updateUserState(userDocumentId:String, newState:UserState){
        userRepository.updateUserState(userDocumentId, newState)
    }
}