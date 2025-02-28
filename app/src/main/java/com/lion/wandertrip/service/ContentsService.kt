package com.lion.wandertrip.service

import com.lion.wandertrip.model.ContentsModel
import com.lion.wandertrip.repository.ContentsRepository

class ContentsService(val contentsRepository: ContentsRepository) {
    // 컨텐츠 가져오는 서비스
    suspend fun getContentByDocId(contentDocId: String): ContentsModel {
        val contentsVO = contentsRepository.getContentByDocId(contentDocId) // 데이터를 가져옴
        return contentsVO.toDetailReviewModel() // 변환 후 반환
    }

    // 컨텐츠 가져오기 contentsID로
    suspend fun getContentByContentsId(contentsId: String): ContentsModel {
        return contentsRepository.getContentByContentsId(contentsId).toDetailReviewModel()
    }

    // 컨텐츠 넣기
    suspend fun addContents(contentModel: ContentsModel): String {
        return contentsRepository.addContents(contentModel.toDetailReviewVO())

    }

    // 컨텐츠 수정하기
    suspend fun modifyContents(contentModel: ContentsModel): Boolean {
        return contentsRepository.modifyContents(contentModel.toDetailReviewVO())
    }

    // 컨텐츠가 존재하면 true 리턴 contenstDocID 를 매개변수로 받을것
    suspend fun isContentExists(contentsDocId: String): String {
        return contentsRepository.isContentExists(contentsDocId)?:""
    }

    // 특정 컨텐츠의 리뷰 평점 평균을 계산하여 ContentsData 문서에 저장하고 리뷰 개수를 반환
    suspend fun updateContentRatingAndRatingCount(contentsDocId: String): Int {
        return contentsRepository.updateContentRatingAndRatingCount(contentsDocId)
    }
}

