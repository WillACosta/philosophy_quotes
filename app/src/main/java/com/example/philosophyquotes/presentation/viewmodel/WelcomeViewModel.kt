package com.example.philosophyquotes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.philosophyquotes.core.constants.AppConstants
import com.example.philosophyquotes.data.repository.local.app_preferences.AppPreferences

class UserData(val isFirstAccess: Boolean, val name: String) {}

class WelcomeViewModel(private val appPreferences: AppPreferences) : ViewModel() {

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> = _userData

    init {
        val isFirstAccess = appPreferences.getData(
            AppConstants.SHARED.IS_FIRST_ACCESS_KEY,
            false
        )

        val userName = appPreferences.getData(
            AppConstants.SHARED.USER_NAME_KEY,
            ""
        )

        _userData.value = UserData(isFirstAccess!!, userName!!)
    }

    fun handleFirstAccess() {
        appPreferences.storeData<Boolean>(AppConstants.SHARED.IS_FIRST_ACCESS_KEY, true)
    }
}