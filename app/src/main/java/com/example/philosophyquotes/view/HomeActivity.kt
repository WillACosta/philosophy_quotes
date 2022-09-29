package com.example.philosophyquotes.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.philosophyquotes.databinding.ActivityHomeBinding
import com.example.philosophyquotes.viewmodel.HomeState
import com.example.philosophyquotes.viewmodel.HomeViewModel
import com.example.philosophyquotes.viewmodel.state.UiState

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
        viewModel.apply {
            uiState.observe(this@HomeActivity, Observer {
                when (it) {
                    is UiState.Loading -> {
                        binding.shimmerLayout.startShimmer()
                    }

                    is UiState.Success -> {
                        handleSuccessState(it.data)
                    }

                    else -> {}
                }
            })
        }

        binding.refreshQuoteButton.setOnClickListener {
            viewModel.getRandomQuote()
        }
    }

    private fun initView() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        supportActionBar?.hide()

        binding.homeContent.apply {
            visibility = View.GONE
        }
    }

    private fun handleSuccessState(state: HomeState?) {
        val name = state?.userName
        val quote = state?.quote

        if (name?.isNotEmpty() == true) {
            binding.textUserName.text = buildString {
                append("Hello, ")
                append(name)
                append("!")
            }
        }

        if (quote != null) {
            binding.quoteDescription.text = quote.quote
            binding.quoteAuthor.text = buildString {
                append("- ")
                append(quote.author)
            }

            binding.shimmerLayout.apply {
                stopShimmer()
                visibility = View.GONE
            }

            binding.homeContent.apply {
                visibility = View.VISIBLE
            }
        }
    }
}
