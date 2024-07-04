package com.alchemtech.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import com.alchemtech.playlistmaker.creators.StringResourcesCreator
import com.alchemtech.playlistmaker.creators.ThemeInteractorCreator
import com.alchemtech.playlistmaker.domain.settings.SettingsInteractor

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        StringResourcesCreator.setApplicationContext(this)
        switchTheme()
    }

    private fun switchTheme() {
        setDefaultNightMode(
            ThemeInteractorCreator
                .provideThemeInteractor(this)
                .getThemeSettings().themeNumber
        )
    }
}