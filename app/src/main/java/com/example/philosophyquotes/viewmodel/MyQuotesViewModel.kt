package com.example.philosophyquotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.philosophyquotes.data.model.Quote
import com.example.philosophyquotes.data.repository.local.CQuotesLocalRepository

class MyQuotesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CQuotesLocalRepository(application.applicationContext)
    private val _quotesList = MutableLiveData<List<Quote>>()

    val quotes: LiveData<List<Quote>> = _quotesList

    fun getQuotes() {
        _quotesList.value = repository.getAll()
    }

    fun deleteById(id: Int) {
        repository.delete(id)
    }

    fun save(quote: Quote) {
        repository.save(quote)
    }
}