package com.example.philosophyquotes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.philosophyquotes.core.constants.AppConstants
import com.example.philosophyquotes.data.listener.ApiListener
import com.example.philosophyquotes.data.model.Quote
import com.example.philosophyquotes.data.repository.local.app_preferences.AppPreferences
import com.example.philosophyquotes.data.repository.remote.quote_remote_repository.QuoteRemoteRepository
import com.example.philosophyquotes.presentation.viewmodel.state.UiState

data class HomeState(val userName: String?, val quote: Quote?)

class HomeViewModel(
    private val appPreferences: AppPreferences,
    private val repository: QuoteRemoteRepository
) : ViewModel() {

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

            }
        })
    }

}