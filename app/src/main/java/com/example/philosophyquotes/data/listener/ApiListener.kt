package com.example.philosophyquotes.data.listener

import com.example.philosophyquotes.core.exceptions.RemoteException

interface ApiListener<T> {
    fun onSuccess(response: T)
    fun onFailure(exception: RemoteException)
}