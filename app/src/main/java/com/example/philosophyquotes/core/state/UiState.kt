package com.example.philosophyquotes.core.state

// State Pattern
sealed class UiState<out R> {
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val exception: Exception) : UiState<Nothing>()

    object Loading : UiState<Nothing>()
}
