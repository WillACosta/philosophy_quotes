package com.example.philosophyquotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.philosophyquotes.constants.AppConstants
import com.example.philosophyquotes.data.repository.local.CAppPreferences

class NameViewModel(application: Application) : AndroidViewModel(application) {
    private val appPreferences = CAppPreferences(application.applicationContext)

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    fun onNameChanged(value: String) {
        _name.value = value
        _error.value = validate()
    }

    fun submitName() {
        if (_error.value == null) {
            appPreferences.storeData(AppConstants.SHARED.USER_NAME_KEY, name)
        }
    }

    private fun validate(): String? {
        val value = _name.value

        if (value != null && value.isEmpty() || value == null) {
            return "Oops! Your name is invalid"
        }

        if (value.length < 4) {
            return "Oops! Your name should not be less than 3 characters"
        }

        return null
    }
}