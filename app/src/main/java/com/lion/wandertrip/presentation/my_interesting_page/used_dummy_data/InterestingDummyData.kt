package com.lion.wandertrip.presentation.my_interesting_page.used_dummy_data

import com.lion.wandertrip.model.UserInterestingModel

class InterestingDummyData {
 companion object{
     val dummyDataList = mutableListOf<UserInterestingModel>(
         UserInterestingModel(
             contentTitle = "경희궁 흥화문",
             smallImagePath = "http://tong.visitkorea.or.kr/cms/resource/55/3384855_image3_1.JPG",
             saveCount = 10,
             contentTypeID = "12",
             ratingScore = 4.3f,
             areacode ="1",
             sigungucode = "23",
             starRatingCount = 15
         ),
         UserInterestingModel(
             contentTitle = "넘버25 호텔 경주터미널점",
             smallImagePath = "",
             saveCount = 13,
             contentTypeID = "32",
             ratingScore = 4.6f,
             areacode ="35",
             sigungucode = "2",
             starRatingCount = 30
         ),
         UserInterestingModel(
             contentTitle = "울산단골식당",
             smallImagePath = "http://tong.visitkorea.or.kr/cms/resource/03/1981703_image3_1.jpg",
             saveCount = 6,
             contentTypeID = "39",
             ratingScore = 3.7f,
             areacode ="7",
             sigungucode = "2",
             starRatingCount = 12
         ),
         UserInterestingModel(
             contentTitle = "강릉 한복 문화 창작소",
             smallImagePath = "http://tong.visitkorea.or.kr/cms/resource/50/3379850_image3_1.jpg",
             saveCount = 8,
             contentTypeID = "12",
             ratingScore = 3.7f,
             areacode ="32",
             sigungucode = "1",
             starRatingCount = 15
         ),
         UserInterestingModel(
             contentTitle = "호텔케니여수",
             smallImagePath = "http://tong.visitkorea.or.kr/cms/resource/29/2803529_image3_1.jpg",
             saveCount = 16,
             contentTypeID = "32",
             ratingScore = 3.4f,
             areacode ="38",
             sigungucode = "13",
             starRatingCount = 20
         ),
         UserInterestingModel(
             contentTitle = "오뎅식당",
             smallImagePath = "http://tong.visitkorea.or.kr/cms/resource/03/1210303_image3_1.jpg",
             saveCount = 44,
             contentTypeID = "39",
             ratingScore = 4.8f,
             areacode ="31",
             sigungucode = "25",
             starRatingCount = 77
         ),

         )
 }
}