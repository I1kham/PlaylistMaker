package com.alchemtech.playlistmaker.data.settings.impl

import android.content.SharedPreferences
import com.alchemtech.playlistmaker.di.THEME
import com.alchemtech.playlistmaker.domain.settings.SettingsRepository

class SettingsRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
) : SettingsRepository {
    override fun getThemeSettings(): Int {
        return sharedPreferences.getInt(THEME, -1)
    }

    override fun updateThemeSetting(themeNumber: Int) {
        sharedPreferences.edit()
            .putInt(THEME, themeNumber)
            .apply()
    }
}