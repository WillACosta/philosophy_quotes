package com.example.philosophyquotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.philosophyquotes.constants.AppConstants
import com.example.philosophyquotes.data.listener.ApiListener
import com.example.philosophyquotes.data.model.Quote
import com.example.philosophyquotes.data.repository.local.CAppPreferences
import com.example.philosophyquotes.data.repository.remote.CQuoteRepository
import com.example.philosophyquotes.viewmodel.state.UiState

data class HomeState(val userName: String?, val quote: Quote?)

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val appPreferences = CAppPreferences(application.applicationContext)
    private val repository = CQuoteRepository(application.applicationContext)

    private val _homeUiState = MutableLiveData<UiState<HomeState>>()
    val uiState: LiveData<UiState<HomeState>> = _homeUiState

    init {
        getRandomQuote()
        getUserNameFromStorage()
    }

    private fun getUserNameFromStorage() {
        _homeUiState.value = UiState.Loading()
        val userName = appPreferences.getData<String>(AppConstants.SHARED.USER_NAME_KEY, "")
        _homeUiState.value = UiState.Success(HomeState(userName, null))
    }

    fun getRandomQuote() {
        _homeUiState.value = UiState.Loading()

        repository.getRandomQuote(object : ApiListener<Quote> {
            override fun onSuccess(response: Quote) {
                _homeUiState.value = UiState.Success(HomeState(null, response))
            }

            override fun onFailure(error: String) {
                TODO("Not yet implemented")
            }
        })
    }
}