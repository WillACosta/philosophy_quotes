package com.example.philosophyquotes.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.philosophyquotes.core.utils.HelperFunctions
import com.example.philosophyquotes.databinding.ActivityNameBinding
import com.example.philosophyquotes.presentation.viewmodel.NameViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class NameActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityNameBinding

    private val _viewModel: NameViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        setListeners()

        setContentView(_binding.root)
    }

    private fun submitUserName() {
        val validName = _binding.edtNameContainer.error == null

        if (validName) {
            _viewModel.submitName()
            goToMainActivity()
            finish()
        }
    }

    private fun setListeners() {
        _binding.buttonSubmit.setOnClickListener {
            submitUserName()
        }

        _binding.edtName.doOnTextChanged { value, _, _, _ ->
            _viewModel.onNameChanged(value.toString())
        }

        _viewModel.error.observe(this) {
            _binding.edtNameContainer.error = it
            _binding.buttonSubmit.isEnabled = it == null
        }
    }

    private fun initView() {
        _binding = ActivityNameBinding.inflate(layoutInflater)
        supportActionBar?.hide()
    }

    private fun goToMainActivity() {
        HelperFunctions.startActivity(this, ContainerActivity::class.java)
    }
}