package com.lion.wandertrip.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.lion.wandertrip.model.TripNoteModel
import com.lion.wandertrip.vo.TripNoteReplyVO
import com.lion.wandertrip.vo.TripNoteVO
import com.lion.wandertrip.vo.TripScheduleVO
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject

class TripNoteRepository@Inject constructor() {

    // 이미지 데이터를 서버로 업로드 하는 메서드
    suspend fun uploadTripNoteImage(sourceFilePath:String, serverFilePath:String){
        // 저장되어 있는 이미지의 경로
        val file = File(sourceFilePath)
        val fileUri = Uri.fromFile(file)
        // 업로드 한다.
        val firebaseStorage = FirebaseStorage.getInstance()
        val childReference = firebaseStorage.reference.child("image/$serverFilePath")
        childReference.putFile(fileUri).await()
    }

    // 여행기 데이터를 저장하는 메서드
    // 새롭게 추가된 문서의 id를 반환한다.
    suspend fun addTripNoteData(tripNoteVO: TripNoteVO) : String{
        val fireStore = FirebaseFirestore.getInstance()
        val collectionReference = fireStore.collection("TripNoteData")
        val documentReference = collectionReference.add(tripNoteVO).await()
        return documentReference.id
    }

    // 여행기 댓글을 저장하는 메서드
    // 새롭게 추가된 문서의 id를 반환한다.
    suspend fun addTripNoteReplyData(tripNoteReplyVO: TripNoteReplyVO) : String{
        val fireStore = FirebaseFirestore.getInstance()
        val collectionReference = fireStore.collection("TripNoteReplyData")
        val documentReference = collectionReference.add(tripNoteReplyVO).await()
        return documentReference.id
    }

    // 댓글 리스트를 가져오는 메서드
    suspend fun selectReplyDataOneById(documentId: String): MutableList<Map<String, *>> {
        val firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("TripNoteReplyData")

        // 데이터를 가져온다.
        val result =
            collectionReference
                .whereEqualTo("tripNoteDocumentId", documentId)
//                .orderBy("tripNoteTimeStamp", Query.Direction.DESCENDING)
                .get()
                .await()

        // 반환할 리스트
        val resultList = mutableListOf<Map<String, *>>()

        // 데이터의 수 만큼 반환한다
        val sortedResult = result.documents.sortedByDescending { it.getTimestamp("tripNoteTimeStamp")}

            // 데이터의 수 만큼 반환한다.
            sortedResult.forEach {
            val tripNoteReplyVO = it.toObject(TripNoteReplyVO::class.java)
            val map = mapOf(
                // 문서의 id
                "tripNoteDocumentId" to it.id,
                // 데이터를 가지고 있는 객체
                "tripNoteReplyVO" to tripNoteReplyVO,
            )
            resultList.add(map)
        }
        return resultList
    }

    // 여행기 리스트 가져오는 메서드
    suspend fun gettingTripNoteList(): MutableList<Map<String, *>> {
        val firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("TripNoteData")
        // 데이터를 가져온다.
        val result =
            collectionReference
                .orderBy("tripNoteTimeStamp", Query.Direction.DESCENDING).get()
                .await()
        // 반환할 리스트
        val resultList = mutableListOf<Map<String, *>>()
        // 데이터의 수 만큼 반환한다.
        result.forEach {
            val tripNoteVO = it.toObject(TripNoteVO::class.java) // TripNoteVO 객체 가져오기
            val tripNoteImage = tripNoteVO.tripNoteImage
            val map = mapOf(
                // 문서의 id
                "documentId" to it.id,
                // 데이터를 가지고 있는 객체
                "tripNoteVO" to tripNoteVO,
                // 이미지 리스트
                "tripNoteImage" to tripNoteImage
            )
            resultList.add(map)
        }
        return resultList
    }

    // 닉네임을 통해 유저의 일정 리스트를 가져오는 메서드
    suspend fun gettingUserScheduleList(userNickName : String): MutableList<Map<String, *>> {
        val firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("TripSchedule")
        // 데이터를 가져온다.
        val result =
            collectionReference
                .whereEqualTo("userNickName", userNickName)
                //.orderBy("scheduleTimeStamp", Query.Direction.DESCENDING)
                .get()
                .await()
        // 반환할 리스트
        val resultList = mutableListOf<Map<String, *>>()
        // 데이터의 수 만큼 반환한다.
        result.forEach {
            val tripScheduleVO = it.toObject(TripScheduleVO::class.java) // TripNoteVO 객체 가져오기
            val map = mapOf(
                // 문서의 id
                "documentId" to it.id,
                // 데이터를 가지고 있는 객체
                "tripScheduleVO" to tripScheduleVO,
            )
            resultList.add(map)
        }
        return resultList
    }

    // 닉네임을 통해 다른 사람 여행기 리스트를 가져온다
    suspend fun gettingOtherTripNoteList(otherNickName : String): MutableList<Map<String, *>> {
        val firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("TripNoteData")
        // 데이터를 가져온다.
        val result =
            collectionReference
                .whereEqualTo("userNickname", otherNickName)
                //.orderBy("scheduleTimeStamp", Query.Direction.DESCENDING)
                .get()
                .await()
        // 반환할 리스트
        val resultList = mutableListOf<Map<String, *>>()
        // 데이터의 수 만큼 반환한다.
        result.forEach {
            val tripNoteVO = it.toObject(TripNoteVO::class.java) // TripNoteVO 객체 가져오기
            val map = mapOf(
                // 문서의 id
                "documentId" to it.id,
                // 데이터를 가지고 있는 객체
                "tripNoteVO" to tripNoteVO,
            )
            resultList.add(map)
        }
        return resultList
    }


//    // 여행기 문서 id를 통해 데이터 가져오기
//    suspend fun selectTripNoteDataOneById(documentId:String) : TripNoteVO{
//        val firestore = FirebaseFirestore.getInstance()
//        val collectionReference = firestore.collection("TripNoteData")
//        val documentReference = collectionReference.document(documentId)
//        val documentSnapShot = documentReference.get().await()
//        val tripNoteVO = documentSnapShot.toObject(TripNoteVO::class.java)!!
//        return tripNoteVO
//    }

    suspend fun selectTripNoteDataOneById(documentId: String): TripNoteVO? {
        val firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("TripNoteData")
        val documentReference = collectionReference.document(documentId)
        val documentSnapShot = documentReference.get().await()

        // 문서가 존재하는지 확인
        if (documentSnapShot.exists()) {
            val tripNoteVO = documentSnapShot.toObject(TripNoteVO::class.java)
            return tripNoteVO // null이 아닐 경우 반환
        } else {
            Log.e("TripNote", "Document with ID $documentId does not exist.")
            return null // 문서가 없으면 null 반환
        }
    }



    suspend fun gettingScheduleById(documentId:String) : TripScheduleVO{
        val firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("TripSchedule")
        val documentReference = collectionReference.document(documentId)
        val documentSnapShot = documentReference.get().await()
        val tripScheduleVO = documentSnapShot.toObject(TripScheduleVO::class.java)!!
        return tripScheduleVO
    }

    // 이미지 데이터를 가져온다
    suspend fun gettingImage(imageFileName:String) : Uri{
        val storageReference = FirebaseStorage.getInstance().reference
        // 파일명을 지정하여 이미지 데이터를 가져온다.
        val childStorageReference = storageReference.child("image/$imageFileName")
        val imageUri = childStorageReference.downloadUrl.await()
        return imageUri
    }

    // 닉네임과 일치하는 유저의 이미지 데이터 가져오기
    suspend fun gettingOtherProfileList(otherNickName:String) : Uri? {
        val firestore = FirebaseFirestore.getInstance()

        // / 닉네임이 일치하는 문서 찾기
        val collectionReference = firestore.collection("UserData")
            .whereEqualTo("userNickName", otherNickName)
            .get()
            .await()

        // 일치하는 문서가 있는지 확인
        val documentSnapshot = collectionReference.documents.firstOrNull()
            ?: return null // 문서가 없으면 null 반환

        // 해당 문서에서 프로필 이미지 경로 가져오기
        val userProfileImageURL = documentSnapshot.getString("userProfileImageURL")

        // userProfileImageURL이 null 또는 비어 있다면 null 반환
        if (userProfileImageURL.isNullOrEmpty()) return null


        val storageReference = FirebaseStorage.getInstance().reference
        // 파일명을 지정하여 이미지 데이터를 가져온다.
        val childStorageReference = storageReference.child("userProfileImage/$userProfileImageURL")
        val imageUri = childStorageReference.downloadUrl.await()

        return imageUri
    }

    // 서버에서 댓글을 삭제한다.
    suspend fun deleteReplyData(documentId:String){
        val firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("TripNoteReplyData")
        val documentReference = collectionReference.document(documentId)
        documentReference.delete().await()
    }

    // 서버에서 여행기를 삭제한다.
    suspend fun deleteTripNoteData(documentId:String){
        val firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("TripNoteData")
        val documentReference = collectionReference.document(documentId)
        documentReference.delete().await()
    }

    // 서버에서 이미지 파일을 삭제한다.
    suspend fun removeImageFile(imageFileName:String){
        val imageReference = FirebaseStorage.getInstance().reference.child("image/$imageFileName")
        imageReference.delete().await()
    }
}