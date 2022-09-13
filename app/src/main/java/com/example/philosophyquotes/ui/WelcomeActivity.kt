package com.example.philosophyquotes.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.philosophyquotes.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var _binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        setContentView(_binding.root)
        setListeners()
    }

    override fun onClick(view: View) {
        goToNameActivity()
        finish()
    }

    private fun setListeners() {
        _binding.startedButton.setOnClickListener(this)
    }

    private fun initView() {
        _binding = ActivityWelcomeBinding.inflate(layoutInflater)
        supportActionBar?.hide()
    }

    private fun goToNameActivity() {
        startActivity(Intent(this, NameActivity::class.java))
    }
}