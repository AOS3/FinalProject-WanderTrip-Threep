package com.lion.wandertrip.vo

import com.google.firebase.Timestamp
import com.lion.wandertrip.model.TripNoteModel

class TripNoteVO {
    var tripNoteDocumentId: String = ""
    var userNickname: String = ""
    var tripNoteTitle: String = ""
    var tripNoteContent: String = ""
    var tripNoteImage: List<String> = emptyList()
    var tripScheduleDocumentId: String = ""
    var tripNoteScrapCount: Int = 0
    var tripNoteTimeStamp: Timestamp = Timestamp.now()
    var tripNoteState: Int = 1

    fun toTripNoteModel(documentId : String): TripNoteModel {
        val tripNoteModel = TripNoteModel()
        tripNoteModel.tripNoteDocumentId = documentId
        tripNoteModel.userNickname = userNickname
        tripNoteModel.tripNoteTitle = tripNoteTitle
        tripNoteModel.tripNoteContent = tripNoteContent
        tripNoteModel.tripNoteImage = tripNoteImage
        tripNoteModel.tripScheduleDocumentId = tripScheduleDocumentId
        tripNoteModel.tripNoteScrapCount = tripNoteScrapCount
        tripNoteModel.tripNoteTimeStamp = tripNoteTimeStamp
        tripNoteModel.tripNoteState = tripNoteState
        return tripNoteModel
    }
}
