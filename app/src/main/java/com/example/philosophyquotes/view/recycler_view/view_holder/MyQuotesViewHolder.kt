package com.example.philosophyquotes.view.recycler_view.view_holder

import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.philosophyquotes.data.model.Quote
import com.example.philosophyquotes.databinding.QuoteListTileBinding
import com.example.philosophyquotes.view.recycler_view.listeners.OnQuoteListener

class MyQuotesViewHolder(
    private val bind: QuoteListTileBinding,
    private val listener: OnQuoteListener
) : RecyclerView.ViewHolder(bind.root) {
    fun bind(quote: Quote) {
        bind.quoteDescription.text = quote.quote
        bind.quoteAuthor.text = quote.author

        bind.itemContainer.setOnClickListener {
            listener.onTouch(quote)
        }

        bind.buttonDelete.setOnClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle("Delete quote")
                .setMessage("Are you sure to delete this quote?\nYou can't undo this action.")
                .setPositiveButton("Delete") { _, _ -> listener.onDelete(quote.id) }
                .setNegativeButton("Cancel", null)
                .create()
                .show()
        }
    }
}