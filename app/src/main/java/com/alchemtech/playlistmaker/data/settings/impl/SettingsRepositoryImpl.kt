package com.alchemtech.playlistmaker.data.settings.impl

import android.content.SharedPreferences
import com.alchemtech.playlistmaker.Constants.THEME
import com.alchemtech.playlistmaker.domain.settings.SettingsRepository
import com.alchemtech.playlistmaker.domain.settings.model.ThemeSettings

class SettingsRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
) : SettingsRepository {
    override fun getThemeSettings(): ThemeSettings {
        return ThemeSettings(
            sharedPreferences.getInt(THEME, 1)
        )
    }

    override fun updateThemeSetting(themeSettings: ThemeSettings) {
        sharedPreferences.edit()
            .putInt(THEME, themeSettings.themeNumber)
            .apply()
    }
}