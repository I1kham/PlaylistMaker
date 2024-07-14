package com.alchemtech.playlistmaker.presentation.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.ActivityStartBinding
import com.alchemtech.playlistmaker.presentation.ui.main.model.StartViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModel<StartViewModel>()

        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setupWithNavController(navController)
        navController.navInflater

        val backPress = onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    bottomNavigationView.visibility = View.VISIBLE
                    // if you want onBackPressed() to be called as normal afterwards
                    if (isEnabled) {
                        isEnabled = false
                        onBackPressed()
                    }
                }
            }
        )
        navHostFragment.childFragmentManager.addFragmentOnAttachListener { fragmentManager, fragment ->
            if (fragment.toString().contains("Player")) {
                bottomNavigationView.visibility = View.GONE
                backPress
            }
        }
    }
}