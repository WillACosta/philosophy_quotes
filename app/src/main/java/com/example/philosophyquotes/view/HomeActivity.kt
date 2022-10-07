package com.example.philosophyquotes.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.philosophyquotes.databinding.ActivityHomeBinding
import com.example.philosophyquotes.utils.HelperFunctions
import com.example.philosophyquotes.utils.ShareContentType
import com.example.philosophyquotes.viewmodel.HomeState
import com.example.philosophyquotes.viewmodel.HomeViewModel
import com.example.philosophyquotes.viewmodel.state.UiState


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    private var quoteToSend = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        setListeners()

        setContentView(binding.root)
    }

    private fun initView() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        supportActionBar?.hide()

        handleContentVisibility("content", View.GONE)
    }

    private fun setListeners() {
        viewModel.apply {
            uiState.observe(this@HomeActivity) {
                when (it) {
                    is UiState.Loading -> {
                        binding.shimmerLayout.startShimmer()
                        handleContentVisibility("content", View.GONE)
                        handleContentVisibility("shimmer", View.VISIBLE)
                    }

                    is UiState.Success -> {
                        handleSuccessState(it.data)
                    }

                    else -> {}
                }
            }
        }

        binding.refreshQuoteButton.setOnClickListener {
            viewModel.getRandomQuote()
        }

        binding.buttonSend.setOnClickListener {
            HelperFunctions.shareContent(this, ShareContentType.Image, quoteToSend)
        }
    }

    private fun handleSuccessState(state: HomeState?) {
        val name = state?.userName
        val quote = state?.quote

        quoteToSend = quote?.quote ?: ""

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

            handleContentVisibility("shimmer", View.GONE)
            handleContentVisibility("content", View.VISIBLE)
            binding.shimmerLayout.stopShimmer()
        }
    }

    private fun handleContentVisibility(element: String, id: Int) {
        when (element) {
            "content" -> {
                binding.homeContent.apply {
                    visibility = id
                }
            }
            "shimmer" -> {
                binding.shimmerLayout.apply {
                    visibility = id
                }
            }
        }
    }
}
