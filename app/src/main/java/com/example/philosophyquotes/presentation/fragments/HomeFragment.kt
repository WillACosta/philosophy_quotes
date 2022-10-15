package com.example.philosophyquotes.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.philosophyquotes.R
import com.example.philosophyquotes.core.utils.HelperFunctions
import com.example.philosophyquotes.core.utils.ShareContentType
import com.example.philosophyquotes.data.model.Quote
import com.example.philosophyquotes.databinding.FragmentHomeBinding
import com.example.philosophyquotes.presentation.viewmodel.HomeState
import com.example.philosophyquotes.presentation.viewmodel.HomeViewModel
import com.example.philosophyquotes.presentation.viewmodel.MyQuotesViewModel
import com.example.philosophyquotes.presentation.viewmodel.state.UiState
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()
    private val myQuotesViewModel: MyQuotesViewModel by viewModel()

    private var quoteToSend = ""
    private var quote: Quote? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

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