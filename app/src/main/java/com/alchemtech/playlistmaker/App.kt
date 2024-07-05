package com.alchemtech.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import com.alchemtech.playlistmaker.creators.ThemeInteractorCreator

class App : Application() {
    override fun onCreate() {
        super.onCreate()
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