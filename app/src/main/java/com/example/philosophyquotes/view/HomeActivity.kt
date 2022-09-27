package com.example.philosophyquotes.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.philosophyquotes.databinding.ActivityHomeBinding
import com.example.philosophyquotes.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        setListeners()

        setContentView(binding.root)
    }

    private fun setListeners() {
        viewModel.state.observe(this) {
            val name = it.userName
            val quote = it.quote

            if (name?.isNotEmpty() == true) {
                binding.textUserName.text = buildString {
                    append("Hello, ")
                    append(name)
                    append(" :)")
                }
            }

            if (quote != null) {
                binding.quoteDescription.text = quote.quote
                binding.quoteAuthor.text = quote.author
            }
        }

        binding.refreshQuoteButton.setOnClickListener {
            viewModel.getRandomQuote()
        }
    }

    private fun initView() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        supportActionBar?.hide()
    }
}