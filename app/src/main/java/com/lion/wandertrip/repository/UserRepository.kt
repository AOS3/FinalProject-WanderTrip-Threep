package com.lion.wandertrip.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.lion.wandertrip.util.UserState
import com.lion.wandertrip.vo.UserVO
import kotlinx.coroutines.tasks.await

class UserRepository {

    // 사용자 아이디를 통해 사용자 데이터를 가져오는 메서드
    suspend fun selectUserDataByUserId(userId:String) : MutableList<UserVO>{
        val firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("UserData")
        val result = collectionReference.whereEqualTo("userId", userId).get().await()
        val userVoList = result.toObjects(UserVO::class.java)
        return userVoList
    }

    // 사용자 닉네임을 통해 사용자 데이터를 가져오는 메서드
    suspend fun selectUserDataByUserNickName(userNickName:String) : MutableList<UserVO>{
        val firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("UserData")
        val result = collectionReference.whereEqualTo("userNickName", userNickName).get().await()
        val userVoList = result.toObjects(UserVO::class.java)
        return userVoList
    }

    // 사용자 정보를 추가하는 메서드
    fun addUserData(userVO: UserVO){
        val firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("UserData")
        collectionReference.add(userVO)
    }

    // 사용자 아이디와 동일한 사용자의 정보 하나를 반환하는 메서드
    suspend fun selectUserDataByUserIdOne(userId:String) : MutableMap<String, *>{
        val firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("UserData")
        val result = collectionReference.whereEqualTo("userId", userId).get().await()
        val userVoList = result.toObjects(UserVO::class.java)

        val userMap = mutableMapOf(
            "user_document_id" to result.documents[0].id,
            "user_vo" to userVoList[0]
        )
        return userMap
    }

    // 자동로그인 토큰값을 갱신하는 메서드
    suspend fun updateUserAutoLoginToken(userDocumentId:String, newToken:String){
        val firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("UserData")
        val documentReference = collectionReference.document(userDocumentId)
        val tokenMap = mapOf(
            "userAutoLoginToken" to newToken
        )
        documentReference.update(tokenMap).await()
    }

    // 자동 로그인 토큰 값으로 사용자 정보를 가져오는 메서드
    suspend fun selectUserDataByLoginToken(loginToken:String) : Map<String, *>?{
        val firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("UserData")
        val resultList = collectionReference.whereEqualTo("userAutoLoginToken", loginToken).get().await()
        val userVOList = resultList.toObjects(UserVO::class.java)
        if(userVOList.isEmpty()){
            return null
        } else {
            val userDocumentId = resultList.documents[0].id
            val returnMap = mapOf(
                "userDocumentId" to userDocumentId,
                "userVO" to userVOList[0]
            )
            return returnMap
        }
    }

    // 사용자 정보 전체를 가져오는 메서드
    suspend fun selectUserDataAll() : MutableList<MutableMap<String, *>>{
        val firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("UserData")
        val result = collectionReference.get().await()
        val userList = mutableListOf<MutableMap<String, *>>()
        result.forEach {
            val userMap = mutableMapOf(
                "user_document_id" to it.id,
                "user_vo" to it.toObject(UserVO::class.java)
            )
            userList.add(userMap)
        }
        return userList
    }

    // 사용자 문서 아이디를 통해 사용자 정보를 가져온다.
    suspend fun selectUserDataByUserDocumentIdOne(userDocumentId:String) : UserVO{
        val firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("UserData")
        val result = collectionReference.document(userDocumentId).get().await()
        val userVO = result.toObject(UserVO::class.java)!!
        return userVO
    }


    // 사용자 데이터를 수정한다.
    suspend fun updateUserData(userVO: UserVO){
        // 수정할 데이터를 담을 맵
     /*   val userMap = mapOf(
            "userPw" to userVO.userPw,
            "userNickName" to userVO.userNickName,
            "userAge" to userVO.userAge,
            "userHobby1" to userVO.userHobby1,
            "userHobby2" to userVO.userHobby2,
            "userHobby3" to userVO.userHobby3,
            "userHobby4" to userVO.userHobby4,
            "userHobby5" to userVO.userHobby5,
            "userHobby6" to userVO.userHobby6,
        )*/
        // 수정할 문서에 접근할 수 있는 객체를 가져온다.
        val firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("UserData")
        val documentReference = collectionReference.document(userVO.userDocId)
        /*documentReference.update(userMap).await()*/
    }

    // 사용자의 상태를 변경하는 메서드
    suspend fun updateUserState(userDocumentId:String, newState: UserState){
        val firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("UserData")
        val documentReference = collectionReference.document(userDocumentId)

        val updateMap = mapOf(
            "userState" to newState.number
        )

        documentReference.update(updateMap).await()
    }
}