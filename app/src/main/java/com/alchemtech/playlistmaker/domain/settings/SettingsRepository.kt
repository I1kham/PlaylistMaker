package com.alchemtech.playlistmaker.domain.settings

interface SettingsRepository {
    fun getThemeSettings(): Int
    fun updateThemeSetting(themeNumber: Int)
}