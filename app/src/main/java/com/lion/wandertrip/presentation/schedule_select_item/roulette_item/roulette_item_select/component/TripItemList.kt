package com.lion.wandertrip.presentation.schedule_select_item.roulette_item.roulette_item_select.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.lion.wandertrip.R
import com.lion.wandertrip.model.TripItemModel
import com.lion.wandertrip.presentation.schedule_select_item.roulette_item.roulette_item_select.RouletteItemSelectViewModel
import com.lion.wandertrip.ui.theme.NanumSquareRound
import com.lion.wandertrip.ui.theme.NanumSquareRoundRegular
import com.lion.wandertrip.util.AccommodationItemCat3.Companion.fromCodeAccommodationItemCat3
import com.lion.wandertrip.util.ContentTypeId
import com.lion.wandertrip.util.RestaurantItemCat3.Companion.fromCodeRestaurantItemCat3
import com.lion.wandertrip.util.TripItemCat2.Companion.fromCodeTripItemCat2

@Composable
fun TripItemList(
    tripItems: List<TripItemModel>,
    selectedItems: List<TripItemModel>,
    viewModel: RouletteItemSelectViewModel,
    onItemClick: (TripItemModel) -> Unit,
    modifier: Modifier = Modifier
) {

    // 관심 항목(즐겨찾기) 여부에 따라 정렬: 즐겨찾기 항목이면 1, 아니면 0을 부여하여 내림차순 정렬
    val sortedTripItemList = tripItems.sortedByDescending { tripItem ->
        if (viewModel.userLikeList.contains(tripItem.contentId)) 1 else 0
    }

    LazyColumn(modifier = modifier) {
        items(sortedTripItemList) { tripItem ->
            val isSelected = selectedItems.contains(tripItem)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onItemClick(tripItem) },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // 카드 그림자 효과
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Color(0xFFB3E5FC) else Color.White
                )
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    // 왼쪽: 이미지 영역 (하트 아이콘 오버레이 포함)
                    Box(
                        modifier = Modifier
                            .size(width = 100.dp, height = 80.dp)
                    ) {
                        Image(
                            painter = if (tripItem.firstImage.isEmpty()) {
                                painterResource(id = R.drawable.ic_hide_image_144dp)
                            } else {
                                rememberAsyncImagePainter(model = tripItem.firstImage)
                            },
                            contentDescription = "Trip Image",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                        // 하트 아이콘 버튼 (즐겨찾기 여부에 따라)
                        if (viewModel.userLikeList.contains(tripItem.contentId)) {
                            IconButton(
                                onClick = { viewModel.removeLikeItem(tripItem.contentId) },
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .size(30.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "즐겨찾기",
                                    tint = Color.Red
                                )
                            }
                        } else {
                            IconButton(
                                onClick = { viewModel.addLikeItem(tripItem.contentId) },
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .size(30.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = "즐겨찾기 아님",
                                    tint = Color.Black
                                )
                            }
                        }
                    }

                    // 오른쪽: 텍스트 영역
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically)
                    ) {
                        // 제목
                        Text(
                            text = tripItem.title,
                            fontSize = 20.sp,
                            fontFamily = NanumSquareRound,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                        // 카테고리
                        Text(
                            text = when(tripItem.contentTypeId) {
                                ContentTypeId.TOURIST_ATTRACTION.contentTypeCode.toString() -> {
                                    fromCodeTripItemCat2(tripItem.cat2)?.catName ?: ""
                                }
                                ContentTypeId.RESTAURANT.contentTypeCode.toString() -> {
                                    fromCodeRestaurantItemCat3(tripItem.cat3)?.catName ?: ""
                                }
                                ContentTypeId.ACCOMMODATION.contentTypeCode.toString() -> {
                                    fromCodeAccommodationItemCat3(tripItem.cat3)?.catName ?: ""
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
                        // 주소
                        Text(
                            text = tripItem.addr1 + tripItem.addr2,
                            fontSize = 12.sp,
                            fontFamily = NanumSquareRoundRegular,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            lineHeight = 14.sp
                        )
                    }
                }
            }
        }
    }
}