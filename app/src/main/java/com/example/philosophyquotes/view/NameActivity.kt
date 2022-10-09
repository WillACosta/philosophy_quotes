package com.example.philosophyquotes.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.philosophyquotes.databinding.ActivityNameBinding
import com.example.philosophyquotes.utils.HelperFunctions
import com.example.philosophyquotes.viewmodel.NameViewModel


class NameActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityNameBinding
    private lateinit var _viewModel: NameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        _viewModel = ViewModelProvider(this)[NameViewModel::class.java]

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