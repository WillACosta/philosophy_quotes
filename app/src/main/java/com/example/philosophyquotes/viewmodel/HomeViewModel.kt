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

data class HomeState(val userName: String?, val quote: Quote?)

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val appPreferences = CAppPreferences(application.applicationContext)
    private val repository = CQuoteRepository(application.applicationContext)

    private val _homeState = MutableLiveData<HomeState>()
    val state: LiveData<HomeState> = _homeState

    init {
        getUserNameFromStorage()
        getRandomQuote()
    }

    private fun getUserNameFromStorage() {
        val userName = appPreferences.getData<String>(AppConstants.SHARED.USER_NAME_KEY, "")
        _homeState.value = HomeState(userName, null)
    }

    fun getRandomQuote() {
        repository.getRandomQuote(object : ApiListener<Quote> {
            override fun onSuccess(response: Quote) {
                _homeState.value = HomeState(null, response)
            }

            override fun onFailure(error: String) {
                TODO("Not yet implemented")
            }
        })
    }
}