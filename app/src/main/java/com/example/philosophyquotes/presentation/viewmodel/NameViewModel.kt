package com.example.philosophyquotes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.philosophyquotes.core.constants.AppConstants
import com.example.philosophyquotes.data.repository.local.app_preferences.AppPreferences

class NameViewModel(private val appPreferences: AppPreferences) : ViewModel() {

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
            appPreferences.storeData(AppConstants.SHARED.USER_NAME_KEY, _name.value!!)
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