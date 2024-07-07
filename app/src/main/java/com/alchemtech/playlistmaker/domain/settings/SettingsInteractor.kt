package com.alchemtech.playlistmaker.domain.settings

import com.alchemtech.playlistmaker.domain.settings.model.ThemeSettings

interface SettingsInteractor {
    fun getThemeSettings(): ThemeSettings
    fun updateThemeSetting(themeSettings: ThemeSettings)
}