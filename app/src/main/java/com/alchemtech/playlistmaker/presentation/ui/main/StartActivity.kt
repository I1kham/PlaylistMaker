package com.alchemtech.playlistmaker.presentation.ui.main

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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

        onBackPressedDispatcher.addCallback(
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (isEnabled) {
                        navController.navigateUp()
                    } else {
                        onBackPressed()
                    }
                }

            }
        )


    }

    fun bottomNavigationVisibility(isVisibile: Boolean) {
        binding.bottomNavigation.isVisible = isVisibile
    }
}