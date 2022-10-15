package com.example.philosophyquotes.presentation.recycler_view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.philosophyquotes.data.model.Quote
import com.example.philosophyquotes.databinding.QuoteListTileBinding
import com.example.philosophyquotes.presentation.recycler_view.listeners.OnQuoteListener
import com.example.philosophyquotes.presentation.recycler_view.view_holder.MyQuotesViewHolder

class MyQuotesAdapter : RecyclerView.Adapter<MyQuotesViewHolder>() {
    private var quotesList: List<Quote> = listOf()
    private lateinit var listener: OnQuoteListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyQuotesViewHolder {
        val item = QuoteListTileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyQuotesViewHolder(item, listener)
    }

    override fun onBindViewHolder(holder: MyQuotesViewHolder, position: Int) {
        holder.bind(quotesList[position])
    }

    override fun getItemCount(): Int = quotesList.size

    fun updateQuotes(quotes: List<Quote>) {
        quotesList = quotes
        notifyDataSetChanged()
    }

    fun attachListener(l: OnQuoteListener) {
        listener = l
    }
}