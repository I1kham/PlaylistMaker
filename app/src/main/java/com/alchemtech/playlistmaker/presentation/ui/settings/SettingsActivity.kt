package com.alchemtech.playlistmaker.presentation.ui.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate.getDefaultNightMode
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.lifecycle.ViewModelProvider
import com.alchemtech.playlistmaker.DARK_THEME
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.presentation.ui.settings.model.SettingsViewModel

class SettingsActivity : AppCompatActivity() {
    private lateinit var viewModel: SettingsViewModel

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)
        viewModel = ViewModelProvider(
            this,
            SettingsViewModel.getViewModelFactory()
        )[SettingsViewModel::class.java]
        backButWork()

        toSupportButWork()

        shareAppButWork()

        termsOfUseButWork()

        darkThemeSwitchWork()


    }

    private fun darkThemeSwitchWork() {
        findViewById<Switch>(R.id.dayNightSwitch).setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setDefaultNightMode(MODE_NIGHT_YES)
            } else {
                setDefaultNightMode(MODE_NIGHT_NO)
            }
        }
        setThemeSwitcherChecked()
    }

    private fun termsOfUseButWork() {
        findViewById<Button>(R.id.buttonTermsOfUse).setOnClickListener {
            viewModel.openTermsOfUse()
        }
    }

    private fun shareAppButWork() {

        findViewById<Button>(R.id.buttonShareApp).setOnClickListener {
            viewModel.shareApp()
        }
    }

    private fun toSupportButWork() {

        findViewById<Button>(R.id.buttonToSupport).setOnClickListener {
            viewModel.openSupport()
        }
    }

    private fun backButWork() {
        findViewById<Button>(R.id.pageSettingsPreview).setOnClickListener {
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        getSharedPreferences(DARK_THEME.toString(), MODE_PRIVATE).edit()
            .putInt(DARK_THEME.toString(), getDefaultNightMode())
            .apply()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setDefaultNightMode(
            getSharedPreferences(
                DARK_THEME.toString(),
                MODE_PRIVATE
            ).getInt(DARK_THEME.toString(), 1)
        )
        setThemeSwitcherChecked()
    }

    private fun setThemeSwitcherChecked() {

        when (getSharedPreferences(DARK_THEME.toString(), MODE_PRIVATE).getInt(
            DARK_THEME.toString(),
            1
        )) {
            2 -> {
                findViewById<Switch>(R.id.dayNightSwitch).isChecked = true
            }

            else -> findViewById<Switch>(R.id.dayNightSwitch).isChecked = false
        }
    }
}
