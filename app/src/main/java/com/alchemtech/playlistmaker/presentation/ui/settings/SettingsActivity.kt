package com.alchemtech.playlistmaker.presentation.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate.getDefaultNightMode
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import com.alchemtech.playlistmaker.databinding.ActivitySettingsBinding
import com.alchemtech.playlistmaker.presentation.ui.settings.model.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : AppCompatActivity() {
    private  val viewModel: SettingsViewModel by viewModel()
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        backButWork()
        toSupportButWork()
        shareAppButWork()
        termsOfUseButWork()
        darkThemeSwitchWork()
    }

    override fun onPause() {
        super.onPause()
        viewModel.setDarkThemeState()
    }

    private fun darkThemeSwitchWork() {
        binding.dayNightSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setDefaultNightMode(MODE_NIGHT_YES)
            } else {
                setDefaultNightMode(MODE_NIGHT_NO)
            }
        }
        setThemeSwitcherChecked()
    }

    private fun termsOfUseButWork() {
        binding.buttonTermsOfUse.setOnClickListener {
            viewModel.openTermsOfUse()
        }
    }

    private fun shareAppButWork() {
        binding.buttonShareApp.setOnClickListener {
            viewModel.shareApp()
        }
    }

    private fun toSupportButWork() {
        binding.buttonToSupport.setOnClickListener {
            viewModel.openSupport()
        }
    }

    private fun backButWork() {
        binding.pageSettingsPreview.setOnClickListener {
            finish()
        }
    }

    private fun setThemeSwitcherChecked() {
        binding.dayNightSwitch.isChecked =
            getDefaultNightMode() == MODE_NIGHT_YES
    }
}
