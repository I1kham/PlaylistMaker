package com.alchemtech.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

const val DARK_THEME: Int = 1

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        switchTheme()
    }

    private fun switchTheme() {
        AppCompatDelegate.setDefaultNightMode(
            getSharedPreferences(
                DARK_THEME.toString(),
                MODE_PRIVATE
            ).getInt(DARK_THEME.toString(), 1)
        )
    }

}