package com.example.philosophyquotes.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.philosophyquotes.databinding.FragmentUserNameBinding
import com.example.philosophyquotes.presentation.viewmodel.NameViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserNameFragment : Fragment() {
    private val binding by lazy {
        FragmentUserNameBinding.inflate(layoutInflater)
    }

    private val viewModel: NameViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.buttonSubmit.setOnClickListener { submitUserName() }

        binding.edtName.doOnTextChanged { value, _, _, _ ->
            viewModel.onNameChanged(value.toString())
        }

        viewModel.error.observe(viewLifecycleOwner) { text ->
            binding.edtNameContainer.error = text
            binding.buttonSubmit.isEnabled = text == null
        }
    }

    private fun submitUserName() {
        val validName = binding.edtNameContainer.error == null

        if (validName) {
            viewModel.submitName()
            findNavController().navigate(
                UserNameFragmentDirections.actionUserNameFragmentToHomeFragment()
            )
        }
    }

}
