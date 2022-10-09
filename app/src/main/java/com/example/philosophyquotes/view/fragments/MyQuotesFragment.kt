package com.example.philosophyquotes.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.philosophyquotes.databinding.FragmentMyQuotesBinding
import com.example.philosophyquotes.view.recycler_view.adapters.MyQuotesAdapter
import com.example.philosophyquotes.view.recycler_view.listeners.OnQuoteListener
import com.example.philosophyquotes.viewmodel.MyQuotesViewModel

class MyQuotesFragment : Fragment() {
    private var _binding: FragmentMyQuotesBinding? = null
    private val binding get() = _binding!!
    private val quotesAdapter = MyQuotesAdapter()

    private lateinit var viewModel: MyQuotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyQuotesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MyQuotesViewModel::class.java]

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
        }
    }

    private fun configureRecyclerView() {
        binding.recyclerQuotes.layoutManager = LinearLayoutManager(context)
        binding.recyclerQuotes.setHasFixedSize(true)
        binding.recyclerQuotes.adapter = quotesAdapter

        val listener = object : OnQuoteListener {
            override fun onTouch(id: Int) {
                TODO("Not yet implemented")
            }

            override fun onDelete(id: Int) {
                TODO("Not yet implemented")
            }
        }

        quotesAdapter.attachListener(listener)
    }
}