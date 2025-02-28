package com.lion.wandertrip.presentation.search_result_page.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.lion.wandertrip.R
import com.lion.wandertrip.model.TripItemModel
import com.lion.wandertrip.ui.theme.NanumSquareRound
import com.lion.wandertrip.ui.theme.NanumSquareRoundRegular
import com.lion.wandertrip.util.AccommodationItemCat3.Companion.fromCodeAccommodationItemCat3
import com.lion.wandertrip.util.ContentTypeId
import com.lion.wandertrip.util.RestaurantItemCat3.Companion.fromCodeRestaurantItemCat3
import com.lion.wandertrip.util.TripItemCat2.Companion.fromCodeTripItemCat2

@Composable
fun SearchItem(
    tripItem: TripItemModel,
    onItemClick: (TripItemModel) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(tripItem) }
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp)
        ) {
            Text(
                text = tripItem.title,
                fontSize = 20.sp,
                fontFamily = NanumSquareRound,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            Text(
                text = when (tripItem.contentTypeId) {
                    ContentTypeId.TOURIST_ATTRACTION.contentTypeCode.toString() -> {
                        "관광지·" + fromCodeTripItemCat2(tripItem.cat2)!!.catName
                    }
                    ContentTypeId.RESTAURANT.contentTypeCode.toString() -> {
                        "음식점·" + fromCodeRestaurantItemCat3(tripItem.cat3)!!.catName
                    }
                    ContentTypeId.ACCOMMODATION.contentTypeCode.toString() -> {
                        "숙소·" + fromCodeAccommodationItemCat3(tripItem.cat3)!!.catName
                    }
                    else -> ""
                },
                fontSize = 12.sp,
                fontFamily = NanumSquareRound,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 14.sp,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            Text(
                text = tripItem.addr1 + tripItem.addr2,
                fontSize = 12.sp,
                fontFamily = NanumSquareRoundRegular,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 14.sp
            )
        }

        Image(
            painter = if (tripItem.firstImage.isEmpty()) {
                painterResource(id = R.drawable.ic_hide_image_144dp)
            } else {
                rememberAsyncImagePainter(model = tripItem.firstImage)
            },
            contentDescription = "Trip Image",
            modifier = Modifier
                .size(width = 140.dp, height = 120.dp)
                .padding(end = 8.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
    }
}
