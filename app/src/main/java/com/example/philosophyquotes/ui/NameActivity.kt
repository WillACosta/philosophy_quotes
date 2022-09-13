package com.example.philosophyquotes.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.philosophyquotes.databinding.ActivityNameBinding


class NameActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var _binding: ActivityNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        setListeners()

        setContentView(_binding.root)
    }

    override fun onClick(view: View) {
        goToMainActivity()
        finish()
    }

    private fun setListeners() {
        _binding.startedButton.setOnClickListener(this)
    }

    private fun initView() {
        _binding = ActivityNameBinding.inflate(layoutInflater)
        supportActionBar?.hide()
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}