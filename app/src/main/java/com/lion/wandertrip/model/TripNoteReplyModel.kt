package com.lion.wandertrip.model

import com.google.firebase.Timestamp
import com.lion.wandertrip.vo.TripNoteReplyVO

class TripNoteReplyModel(
    var tripNoteReplyDocId: String = "",   // 댓글 문서 ID
    var userNickname: String = "",      // 작성자 닉네임
    var replyText: String = "",         // 댓글 내용
    var replyState: Int = 1,            // 댓글 상태 (1: 활성화, 2: 삭제)
    var replyTimeStamp: Timestamp = Timestamp.now(), // 댓글 등록 시간
    var tripNoteDocumentId: String = "" // 여행기 문서 ID
) {
    fun toReplyItemVO(): TripNoteReplyVO {
        val replyItemVO = TripNoteReplyVO()
        replyItemVO.tripNoteReplyDocId = tripNoteReplyDocId
        replyItemVO.userNickname = userNickname
        replyItemVO.replyText = replyText
        replyItemVO.replyState = replyState
        replyItemVO.replyTimeStamp = replyTimeStamp
        replyItemVO.tripNoteDocumentId = tripNoteDocumentId
        return replyItemVO
    }
}