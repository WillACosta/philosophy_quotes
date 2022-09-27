package com.example.philosophyquotes.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        handleStoreUserName()
    }

    private fun handleStoreUserName() {
        val name = _binding.edtName.text.toString()

        if (name.isEmpty()) {
            Toast.makeText(this, "Oops! Your name is invalid", Toast.LENGTH_SHORT).show()
        } else {
            _viewModel.storeUserName(name)
            goToMainActivity()
            finish()
        }
    }

    private fun setListeners() {
        _binding.startedButton.setOnClickListener(this)
    }

    private fun initView() {
        _binding = ActivityNameBinding.inflate(layoutInflater)
        supportActionBar?.hide()
    }

    private fun goToMainActivity() {
        HelperFunctions.startActivity(this, HomeActivity::class.java)
    }
}