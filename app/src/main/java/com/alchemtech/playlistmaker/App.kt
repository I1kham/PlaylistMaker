package com.alchemtech.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode

const val DARK_THEME = 1

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        switchTheme()

    }

    private fun switchTheme() {
        setDefaultNightMode(
            this.getSharedPreferences(/* name = */ DARK_THEME.toString(), /* mode = */ MODE_PRIVATE)
                .getInt(DARK_THEME.toString(), 1)
        )

    }
}