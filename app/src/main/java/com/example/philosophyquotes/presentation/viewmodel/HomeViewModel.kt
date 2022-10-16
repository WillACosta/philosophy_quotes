package com.example.philosophyquotes.presentation.viewmodel

import androidx.lifecycle.*
import com.example.philosophyquotes.core.constants.AppConstants
import com.example.philosophyquotes.core.exceptions.RemoteException
import com.example.philosophyquotes.core.state.UiState
import com.example.philosophyquotes.data.model.Quote
import com.example.philosophyquotes.data.repository.local.app_preferences.AppPreferences
import com.example.philosophyquotes.data.repository.remote.quote_remote_repository.QuoteRemoteRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
    private val appPreferences: AppPreferences,
    private val repository: QuoteRemoteRepository
) : ViewModel() {

    private val _userName = MutableLiveData<String>()
    private val userName: LiveData<String> = _userName

    private val _uiState = MutableLiveData<UiState<Quote>>()
    val uiState: LiveData<UiState<Quote>> = _uiState

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBar = MutableLiveData<String?>(null)
    val snackBar: LiveData<String?> = _snackBar

    val userNameText = Transformations.map(userName) { name ->
        buildString {
            append("Hello, ")
            append(name)
            append("!")
        }
    }

    var quote: Quote? = null

    init {
        getUserNameFromStorage()
        getRandomQuote()
    }

    fun showLoading() {
        _isLoading.value = true
    }

    fun hideLoading() {
        _isLoading.value = false
    }

    fun onSnackBarShown() {
        _snackBar.value = null
    }

    fun getRandomQuote() {
        viewModelScope.launch {
            repository.getRandomQuote()
                .onStart {
                    _uiState.postValue(UiState.Loading)
                    delay(900)
                }
                .catch { e ->
                    val error = e.message ?: "Unable to retrieve a quote!"
                    _uiState.postValue(UiState.Error(RemoteException(error)))
                    _snackBar.value = error
                }
                .collect {
                    quote = it
                    _uiState.postValue(UiState.Success(it))
                }
        }
    }

    private fun getUserNameFromStorage() {
        val userName = appPreferences.getData(AppConstants.SHARED.USER_NAME_KEY, "")
        _userName.value = userName
    }

}