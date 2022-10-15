package com.example.philosophyquotes.presentation.recycler_view.listeners

import com.example.philosophyquotes.data.model.Quote

interface OnQuoteListener {
    fun onTouch(quote: Quote)
    fun onDelete(id: Int)
}