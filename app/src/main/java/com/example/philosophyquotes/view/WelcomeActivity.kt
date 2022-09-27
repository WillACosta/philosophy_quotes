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
        _viewModel = ViewModelProvider(this)[WelcomeViewModel::class.java]

        setListeners()
        setContentView(_binding.root)
    }

    override fun onClick(view: View) {
        _viewModel.handleFirstAccess()
        goToNameActivity()
    }

    private fun setListeners() {
        _binding.startedButton.setOnClickListener(this)
        _viewModel.userData.observe(this) {
            if (it.isFirstAccess && it.name.isNotEmpty()) {
                gotoHomeActivity()
                finish()
            }

            if (it.isFirstAccess && it.name.isEmpty()) {
                goToNameActivity()
                finish()
            }
        }
    }

    private fun initView() {
        _binding = ActivityWelcomeBinding.inflate(layoutInflater)
        supportActionBar?.hide()
    }

    private fun goToNameActivity() {
        startActivity(NameActivity::class.java)
    }

    private fun gotoHomeActivity() {
        startActivity(HomeActivity::class.java)
    }

    private fun <T> startActivity(className: Class<T>) {
        startActivity(Intent(this, className))
    }
}