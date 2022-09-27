package com.example.philosophyquotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.philosophyquotes.constants.AppConstants
import com.example.philosophyquotes.data.datasource.local.CAppPreferences

class UserData(val isFirstAccess: Boolean, val name: String) {}

class WelcomeViewModel(application: Application) : AndroidViewModel(application) {
    private val appPreferences = CAppPreferences(application.applicationContext)

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

        _userData.value = UserData(isFirstAccess, userName)
    }

    fun handleFirstAccess() {
        appPreferences.storeData<Boolean>(AppConstants.SHARED.IS_FIRST_ACCESS_KEY, true)
    }
}