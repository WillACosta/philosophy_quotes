package com.example.philosophyquotes.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.philosophyquotes.R
import com.example.philosophyquotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpView()
        setListeners()
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    private fun setUpView() {
        binding = ActivityMainBinding.inflate(layoutInflater)

        val navHost = supportFragmentManager.findFragmentById(NAV_HOST_ID) as NavHostFragment
        navController = navHost.navController

        binding.bottomNavigationBar.setupWithNavController(navController)

        supportActionBar?.hide()

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.myQuotesFragment)
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setListeners() {
        navController.addOnDestinationChangedListener { _, _, args ->
            binding.bottomNavigationBar.isVisible =
                args?.getBoolean(
                    "showBottomNavigationBar",
                    false
                ) == true
        }

        binding.bottomNavigationBar.setOnItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.myQuotesFragment) {
                navController.navigate(R.id.myQuotesFragment)
            }

            true
        }
    }

    companion object {
        const val NAV_HOST_ID = R.id.nav_host_fragment
    }
}
