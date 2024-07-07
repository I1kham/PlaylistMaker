package com.alchemtech.playlistmaker.data.settings.impl

import android.content.SharedPreferences
import com.alchemtech.playlistmaker.domain.settings.SettingsRepository
import com.alchemtech.playlistmaker.domain.settings.model.ThemeSettings

class SettingsRepositoryImpl(
    private val key: String,
    private val sharedPreferences: SharedPreferences,
) : SettingsRepository {
    override fun getThemeSettings(): ThemeSettings {
        return ThemeSettings(
            sharedPreferences.getInt(key, 1)
        )
    }

    override fun updateThemeSetting(themeSettings: ThemeSettings) {
        sharedPreferences.edit()
            .putInt(key, themeSettings.themeNumber)
            .apply()
    }
}