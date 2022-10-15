package com.example.philosophyquotes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.philosophyquotes.data.model.Quote
import com.example.philosophyquotes.data.repository.local.quotes_local_repository.QuotesLocalRepository

class MyQuotesViewModel(private val repository: QuotesLocalRepository) : ViewModel() {

    private val _quotesList = MutableLiveData<List<Quote>>()
    val quotes: LiveData<List<Quote>> = _quotesList

    fun getQuotes() {
        _quotesList.value = repository.getAll()
    }

    fun deleteById(id: Int) {
        repository.delete(id)
    }

    fun save(quote: Quote) {
        val storedQuote = getQuoteByID(quote.id)

        if (storedQuote != null && storedQuote.id == quote.id) {
            deleteById(quote.id)
        } else {
            repository.save(quote)
        }
    }

    fun getQuoteByID(id: Int): Quote? {
        return repository.getQuoteByID(id)
    }

}