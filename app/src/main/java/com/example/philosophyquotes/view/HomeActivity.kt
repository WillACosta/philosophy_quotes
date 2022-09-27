package com.example.philosophyquotes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.philosophyquotes.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        setListeners()

        setContentView(binding.root)
    }

    override fun onClick(view: View) {}

    private fun setListeners() {}

    private fun initView() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        supportActionBar?.hide()
    }
}