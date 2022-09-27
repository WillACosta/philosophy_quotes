package com.example.philosophyquotes.data.listener

interface ApiListener<T> {
    fun onSuccess(response: T)
    fun onFailure(error: String)
}