package com.example.philosophyquotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.philosophyquotes.constants.AppConstants
import com.example.philosophyquotes.data.datasource.local.CAppPreferences

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val appPreferences = CAppPreferences(application.applicationContext)

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    init {
        getUserNameFromStorage()
    }

    private fun getUserNameFromStorage() {
        _userName.value = appPreferences.getData<String>(AppConstants.SHARED.USER_NAME_KEY, "")
    }
}