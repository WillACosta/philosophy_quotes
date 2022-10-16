package com.example.philosophyquotes.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.philosophyquotes.R
import com.example.philosophyquotes.core.state.UiState
import com.example.philosophyquotes.core.utils.HelperFunctions
import com.example.philosophyquotes.core.utils.ShareContentType
import com.example.philosophyquotes.data.model.Quote
import com.example.philosophyquotes.databinding.FragmentHomeBinding
import com.example.philosophyquotes.presentation.viewmodel.HomeViewModel
import com.example.philosophyquotes.presentation.viewmodel.MyQuotesViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val viewModel: HomeViewModel by viewModel()
    private val myQuotesViewModel: MyQuotesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initViewBinding()
        setListeners()

        return binding.root
    }

    private fun initViewBinding() {
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setListeners() {
        viewModel.apply {
            uiState.observe(viewLifecycleOwner) { state ->
                when (state) {
                    UiState.Loading -> {
                        viewModel.showLoading()
                        binding.shimmerLayout.startShimmer()
                    }

                    is UiState.Success -> {
                        viewModel.hideLoading()
                        handleSuccessState(state.data)
                        binding.shimmerLayout.stopShimmer()
                    }

                    is UiState.Error -> {
                        viewModel.hideLoading()
                        binding.shimmerLayout.stopShimmer()
                    }
                }
            }
        }

        viewModel.snackBar.observe(viewLifecycleOwner) {
            it?.let { error ->
                Snackbar.make(
                    binding.root,
                    error,
                    Snackbar.LENGTH_LONG
                ).show()

                viewModel.onSnackBarShown()
            }
        }

        binding.refreshQuoteButton.setOnClickListener {
            viewModel.getRandomQuote()
        }

        binding.buttonFavorite.setOnClickListener {
            val condition = viewModel.quote != null

            if (condition) {
                myQuotesViewModel.save(viewModel.quote!!)
            }

            handleFavoriteButton(condition)
        }

        binding.buttonSend.setOnClickListener {
            val quote = viewModel.quote

            if (context != null && quote != null) {
                HelperFunctions.shareContent(
                    requireContext(),
                    ShareContentType.Image,
                    quote.quote
                )
            }
        }
    }

    private fun handleSuccessState(quote: Quote) {
        binding.quoteDescription.text = quote.quote
        binding.quoteAuthor.text = quote.authorName

        verifyIfQuoteIsAlreadyStored(quote.id)
    }

    private fun verifyIfQuoteIsAlreadyStored(id: Int) {
        val storedQuote = myQuotesViewModel.getQuoteByID(id)
        handleFavoriteButton(storedQuote != null)
    }

    private fun handleFavoriteButton(hasLiked: Boolean) {
        if (hasLiked) {
            binding.buttonFavorite.setImageResource(R.drawable.ic_fill_heart)
        } else {
            binding.buttonFavorite.setImageResource(R.drawable.ic_heart)
        }
    }

}