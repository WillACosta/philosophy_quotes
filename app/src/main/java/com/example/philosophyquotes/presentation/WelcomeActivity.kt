package com.example.philosophyquotes.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.philosophyquotes.core.utils.HelperFunctions
import com.example.philosophyquotes.databinding.ActivityWelcomeBinding
import com.example.philosophyquotes.presentation.viewmodel.WelcomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class WelcomeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var _binding: ActivityWelcomeBinding

    private val viewModel: WelcomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        setListeners()

        setContentView(_binding.root)
    }

    override fun onClick(view: View) {
        viewModel.handleFirstAccess()
        goToNameActivity()
    }

    private fun setListeners() {
        _binding.startedButton.setOnClickListener(this)
        viewModel.userData.observe(this) {
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
        HelperFunctions.startActivity(this, NameActivity::class.java)
    }

    private fun gotoHomeActivity() {
        HelperFunctions.startActivity(this, ContainerActivity::class.java)
    }
}