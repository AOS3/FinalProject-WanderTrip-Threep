package com.lion.wandertrip.presentation.detail_page.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lion.wandertrip.presentation.detail_page.DetailViewModel
import com.lion.wandertrip.util.CustomFont

@Composable
fun BasicInfoDescriptionColumn(detailViewModel : DetailViewModel) {
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
        Row(modifier = Modifier.padding(vertical = 10.dp)) {
            Text(
                "주소 : ",
                fontFamily = CustomFont.customFontBold,
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(
                detailViewModel.tripCommonContentModelValue.value.addr1?:"",
                fontFamily = CustomFont.customFontRegular
            )
        }
        if(detailViewModel.tripCommonContentModelValue.value.tel !="")
        Row(modifier = Modifier.padding(vertical = 10.dp)) {
            Text(
                "전화 : ",
                fontFamily = CustomFont.customFontBold,
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(
                detailViewModel.tripCommonContentModelValue.value.tel?:"",
                fontFamily = CustomFont.customFontRegular,
                modifier = Modifier.clickable {
                    detailViewModel.onClickTextTel(detailViewModel.tripCommonContentModelValue.value.tel?:"")
                }
            )
        }
        val homePage = detailViewModel.getHomePage(detailViewModel.tripCommonContentModelValue.value.homepage?:"")
        if(homePage!="")
        Row(modifier = Modifier.padding(vertical = 10.dp)) {
            Text(
                "홈페이지 : ",
                fontFamily = CustomFont.customFontBold,
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(
                homePage.toString(),
                fontFamily = CustomFont.customFontRegular,
                modifier = Modifier.clickable {

                    detailViewModel.onClickTextHomepage(homePage.toString())
                })
        }
    }


}