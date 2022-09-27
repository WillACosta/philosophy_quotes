package com.example.philosophyquotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.philosophyquotes.constants.AppConstants
import com.example.philosophyquotes.data.datasource.local.CAppPreferences

class WelcomeViewModel(application: Application) : AndroidViewModel(application) {
    private val appPreferences = CAppPreferences(application.applicationContext)

    private val _isFirstAccess = MutableLiveData<Boolean>()
    val isFirstAccess: LiveData<Boolean> = _isFirstAccess

    init {
       _isFirstAccess.value = appPreferences.getData(AppConstants.SHARED.IS_FIRST_ACCESS, false)
    }

    fun handleFirstAccess() {
        appPreferences.storeData<Boolean>(AppConstants.SHARED.IS_FIRST_ACCESS, true)
    }
}