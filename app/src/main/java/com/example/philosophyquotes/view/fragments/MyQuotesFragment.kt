package com.example.philosophyquotes.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.philosophyquotes.databinding.FragmentMyQuotesBinding

class MyQuotesFragment : Fragment() {
    private var _binding: FragmentMyQuotesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyQuotesBinding.inflate(inflater, container, false)

        return binding.root
    }
}