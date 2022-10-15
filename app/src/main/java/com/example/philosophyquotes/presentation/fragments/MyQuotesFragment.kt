package com.example.philosophyquotes.presentation.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.philosophyquotes.data.model.Quote
import com.example.philosophyquotes.databinding.FragmentMyQuotesBinding
import com.example.philosophyquotes.presentation.recycler_view.adapters.MyQuotesAdapter
import com.example.philosophyquotes.presentation.recycler_view.listeners.OnQuoteListener
import com.example.philosophyquotes.presentation.viewmodel.MyQuotesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyQuotesFragment : Fragment() {
    private var _binding: FragmentMyQuotesBinding? = null
    private val binding get() = _binding!!
    private val quotesAdapter = MyQuotesAdapter()

    private val viewModel: MyQuotesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyQuotesBinding.inflate(inflater, container, false)

        setListeners()
        configureRecyclerView()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getQuotes()
    }

    private fun setListeners() {
        viewModel.quotes.observe(viewLifecycleOwner) {
            quotesAdapter.updateQuotes(it)
            checkIfHasAnyData(it)
        }
    }

    private fun checkIfHasAnyData(data: List<Quote>) {
        if (data.isEmpty()) {
            binding.emptyContent.visibility = View.VISIBLE
            binding.recyclerQuotes.visibility = View.GONE
        } else {
            binding.emptyContent.visibility = View.GONE
            binding.recyclerQuotes.visibility = View.VISIBLE
        }
    }

    private fun configureRecyclerView() {
        binding.recyclerQuotes.layoutManager = LinearLayoutManager(context)
        binding.recyclerQuotes.setHasFixedSize(true)
        binding.recyclerQuotes.adapter = quotesAdapter

        val listener = object : OnQuoteListener {
            override fun onTouch(quote: Quote) {
                (requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).apply {
                    setPrimaryClip(ClipData.newPlainText("quote", quote.quote))
                }

                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2)
                    Toast.makeText(requireActivity(), "Copied", Toast.LENGTH_SHORT).show()
            }

            override fun onDelete(id: Int) {
                viewModel.deleteById(id)
                viewModel.getQuotes()
            }
        }

        quotesAdapter.attachListener(listener)
    }
}