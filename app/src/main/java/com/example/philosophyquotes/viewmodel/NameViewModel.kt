package com.example.philosophyquotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.philosophyquotes.constants.AppConstants
import com.example.philosophyquotes.data.datasource.local.CAppPreferences

class NameViewModel(application: Application) : AndroidViewModel(application) {
    private val appPreferences = CAppPreferences(application.applicationContext)

    fun storeUserName(name: String) {
        appPreferences.storeData<String>(AppConstants.SHARED.USER_NAME_KEY, name)
    }
}