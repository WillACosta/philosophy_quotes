package com.example.philosophyquotes.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.philosophyquotes.databinding.ActivityHomeBinding
import com.example.philosophyquotes.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        setListeners()

        setContentView(binding.root)
    }

    override fun onClick(view: View) {}

    private fun setListeners() {
        viewModel.userName.observe(this) {
            if (it.isNotEmpty()) {
                binding.textUserName.text = buildString {
                    append("Hello, ")
                    append(it)
                }
            }
        }
    }

    private fun initView() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        supportActionBar?.hide()
    }
}