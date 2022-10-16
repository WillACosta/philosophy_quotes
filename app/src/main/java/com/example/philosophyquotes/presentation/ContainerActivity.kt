package com.example.philosophyquotes.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.philosophyquotes.R
import com.example.philosophyquotes.databinding.ActivityContainerBinding
import com.example.philosophyquotes.presentation.fragments.HomeFragment
import com.example.philosophyquotes.presentation.fragments.MyQuotesFragment
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

class ContainerActivity : AppCompatActivity() {

    private val binding: ActivityContainerBinding by lazy {
        ActivityContainerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setupKoinFragmentFactory()
        handleFragments()

        setContentView(binding.root)
    }

    private fun handleFragments() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, HomeFragment::class.java, null)
            .commit()

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_menu_item -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container, HomeFragment::class.java, null)
                        .commit()

                    true
                }

                R.id.quotes_menu_item -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container, MyQuotesFragment::class.java, null)
                        .commit()

                    true
                }

                else -> false
            }
        }
    }
}
