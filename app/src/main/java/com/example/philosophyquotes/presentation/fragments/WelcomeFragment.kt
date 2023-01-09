package com.example.philosophyquotes.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.philosophyquotes.databinding.FragmentWelcomeBinding
import com.example.philosophyquotes.presentation.viewmodel.WelcomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class WelcomeFragment : Fragment(), View.OnClickListener {
    private val _binding by lazy {
        FragmentWelcomeBinding.inflate(layoutInflater)
    }

    private val viewModel: WelcomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    override fun onClick(v: View?) {
        viewModel.handleFirstAccess()
        findNavController().navigate(
            WelcomeFragmentDirections.actionWelcomeFragmentToUserNameFragment()
        )
    }

    private fun setListeners() {
        _binding.startedButton.setOnClickListener(this)

        viewModel.userData.observe(viewLifecycleOwner) {
            if (it.isFirstAccess && it.name.isNotEmpty()) {
                findNavController().navigate(
                    WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment()
                )
            }

            if (it.isFirstAccess && it.name.isEmpty()) {
                findNavController().navigate(
                    WelcomeFragmentDirections.actionWelcomeFragmentToUserNameFragment()
                )
            }
        }
    }

}
