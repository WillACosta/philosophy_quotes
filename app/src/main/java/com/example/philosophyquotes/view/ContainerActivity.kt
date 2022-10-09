package com.example.philosophyquotes.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.philosophyquotes.R
import com.example.philosophyquotes.databinding.ActivityContainerBinding

import com.example.philosophyquotes.view.fragments.HomeFragment
import com.example.philosophyquotes.view.fragments.MyQuotesFragment

class ContainerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContainerBinding

    private val homeFragment = HomeFragment()
    private val myQuotesFragment = MyQuotesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        binding = ActivityContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleFragments()
    }

    private fun handleFragments() {
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, homeFragment)
            .commit()

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_menu_item -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container, homeFragment)
                        .commit()

                    true
                }

                R.id.quotes_menu_item -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container, myQuotesFragment)
                        .commit()

                    true
                }

                else -> false
            }
        }
    }
}
