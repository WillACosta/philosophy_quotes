package com.example.philosophyquotes.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.philosophyquotes.databinding.ActivityNameBinding
import com.example.philosophyquotes.utils.HelperFunctions
import com.example.philosophyquotes.viewmodel.NameViewModel


class NameActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var _binding: ActivityNameBinding
    private lateinit var _viewModel: NameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        _viewModel = ViewModelProvider(this)[NameViewModel::class.java]

        setListeners()

        setContentView(_binding.root)
    }

    override fun onClick(view: View) {
        submitUserName()
    }

    private fun submitUserName() {
        _binding.edtNameContainer.error = validateName()
        val validName = _binding.edtNameContainer.error == null

        if (validName) {
            _viewModel.storeUserName(_binding.edtName.text.toString())
            goToMainActivity()
            finish()
        }
    }

    private fun setListeners() {
        _binding.startedButton.setOnClickListener(this)

        _binding.edtName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                _binding.edtNameContainer.error = validateName()
            }
        }

        _binding.edtName.doOnTextChanged { _, _, _, _ ->
            _binding.edtNameContainer.error = validateName()
        }
    }

    private fun validateName(): String? {
        val name = _binding.edtName.text.toString()

        if (name.isEmpty()) {
            return "Oops! Your name is invalid"
        }

        if (name.length < 4) {
            return "Oops! Your name should not be less than 3 characters"
        }

        return null
    }

    private fun initView() {
        _binding = ActivityNameBinding.inflate(layoutInflater)
        supportActionBar?.hide()
    }

    private fun goToMainActivity() {
        HelperFunctions.startActivity(this, HomeActivity::class.java)
    }
}