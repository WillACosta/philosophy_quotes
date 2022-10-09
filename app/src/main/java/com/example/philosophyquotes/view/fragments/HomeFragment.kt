package com.example.philosophyquotes.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.philosophyquotes.R
import com.example.philosophyquotes.data.model.Quote
import com.example.philosophyquotes.databinding.FragmentHomeBinding
import com.example.philosophyquotes.utils.HelperFunctions
import com.example.philosophyquotes.utils.ShareContentType
import com.example.philosophyquotes.viewmodel.HomeState
import com.example.philosophyquotes.viewmodel.HomeViewModel
import com.example.philosophyquotes.viewmodel.MyQuotesViewModel
import com.example.philosophyquotes.viewmodel.state.UiState

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel
    private lateinit var myQuotesViewModel: MyQuotesViewModel

    private var quoteToSend = ""
    private var quote: Quote? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        myQuotesViewModel = ViewModelProvider(this)[MyQuotesViewModel::class.java]

        handleContentVisibility("content", View.GONE)
        setListeners()

        return binding.root
    }

    private fun setListeners() {
        viewModel.apply {
            uiState.observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Loading -> {
                        binding.shimmerLayout.startShimmer()
                        handleContentVisibility("content", View.GONE)
                        handleContentVisibility("shimmer", View.VISIBLE)
                    }

                    is UiState.Success -> {
                        handleSuccessState(it.data)
                    }

                    is UiState.Error -> {}
                }
            }
        }

        binding.refreshQuoteButton.setOnClickListener {
            viewModel.getRandomQuote()
        }

        binding.buttonFavorite.setOnClickListener {
            if (this.quote != null) {
                myQuotesViewModel.save(quote!!)
                binding.buttonFavorite.setImageResource(R.drawable.ic_fill_heart)
            } else {
                binding.buttonFavorite.setImageResource(R.drawable.ic_heart)
            }
        }

        binding.buttonSend.setOnClickListener {
            if (context != null) {
                HelperFunctions.shareContent(requireContext(), ShareContentType.Image, quoteToSend)
            }
        }
    }

    private fun handleSuccessState(state: HomeState?) {
        val name = state?.userName
        val quote = state?.quote

        quoteToSend = quote?.quote ?: ""
        this.quote = quote

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

            verifyIfQuoteHasAlreadyStored(quote.id)

            handleContentVisibility("shimmer", View.GONE)
            handleContentVisibility("content", View.VISIBLE)
            binding.shimmerLayout.stopShimmer()
        }
    }

    private fun verifyIfQuoteHasAlreadyStored(id: Int) {
        val storedQuote = myQuotesViewModel.getQuoteByID(id)

        if (storedQuote != null) {
            binding.buttonFavorite.setImageResource(R.drawable.ic_fill_heart)
        } else {
            binding.buttonFavorite.setImageResource(R.drawable.ic_heart)
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