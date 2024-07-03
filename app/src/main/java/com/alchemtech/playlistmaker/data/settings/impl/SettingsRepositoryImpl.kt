package com.alchemtech.playlistmaker.data.settings.impl

import android.content.Context
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.alchemtech.playlistmaker.domain.settings.SettingsRepository
import com.alchemtech.playlistmaker.domain.settings.model.ThemeSettings

class SettingsRepositoryImpl(val context: Context) : SettingsRepository {
    override fun getThemeSettings(): ThemeSettings {
        return ThemeSettings(
            context.getSharedPreferences(
                THEME,
                MODE_PRIVATE
            ).getInt(THEME, 1)
        )
    }

    override fun updateThemeSetting(themeSettings: ThemeSettings) {
        context.getSharedPreferences(THEME, MODE_PRIVATE).edit()
            .putInt(THEME, themeSettings.themeNumber)
            .apply()
    }

    companion object {
        val THEME = "THEME"
    }
}