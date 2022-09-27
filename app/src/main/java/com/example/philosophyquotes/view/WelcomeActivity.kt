package com.example.philosophyquotes.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.philosophyquotes.databinding.ActivityWelcomeBinding
import com.example.philosophyquotes.viewmodel.WelcomeViewModel

class WelcomeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var _binding: ActivityWelcomeBinding
    private lateinit var _viewModel: WelcomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        setContentView(_binding.root)
        setListeners()
    }

    override fun onClick(view: View) {
        _viewModel.handleFirstAccess()
    }

    private fun setListeners() {
        _binding.startedButton.setOnClickListener(this)
        _viewModel.isFirstAccess.observe(this) {
            if (it) {
                goToNameActivity()
                finish()
            }
        }
    }

    private fun initView() {
        _binding = ActivityWelcomeBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        _viewModel = ViewModelProvider(this)[WelcomeViewModel::class.java]
    }

    private fun goToNameActivity() {
        startActivity(Intent(this, NameActivity::class.java))
    }
}