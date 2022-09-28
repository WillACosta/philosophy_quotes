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
import com.example.philosophyquotes.viewmodel.state.State

data class HomeState(val userName: String?, val quote: Quote?)

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val appPreferences = CAppPreferences(application.applicationContext)
    private val repository = CQuoteRepository(application.applicationContext)

    private val _homeState = MutableLiveData<State<HomeState>>()
    val state: LiveData<State<HomeState>> = _homeState

    init {
        getUserNameFromStorage()
        getRandomQuote()
    }

    private fun getUserNameFromStorage() {
        _homeState.value = State.Loading()
        val userName = appPreferences.getData<String>(AppConstants.SHARED.USER_NAME_KEY, "")
        _homeState.value = State.Success(HomeState(userName, null))
    }

    fun getRandomQuote() {
        _homeState.value = State.Loading()

        repository.getRandomQuote(object : ApiListener<Quote> {
            override fun onSuccess(response: Quote) {
                _homeState.value = State.Success(HomeState(null, response))
            }

            override fun onFailure(error: String) {
                TODO("Not yet implemented")
            }
        })
    }
}