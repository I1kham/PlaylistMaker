package com.alchemtech.playlistmaker.data.settings

import com.alchemtech.playlistmaker.domain.settings.model.ThemeSettings

interface SettingsRepository {
    fun getThemeSettings(): ThemeSettings
    fun updateThemeSetting(themeSettings: ThemeSettings)
}