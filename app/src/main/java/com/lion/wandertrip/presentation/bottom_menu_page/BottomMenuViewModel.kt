package com.lion.wandertrip.presentation.bottom_menu_page

import android.content.Context
import androidx.lifecycle.ViewModel
import com.lion.wandertrip.TripApplication
import com.lion.wandertrip.service.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class BottomMenuViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel() {
    val tripApplication = context as TripApplication


}


