package com.alchemtech.playlistmaker.domain.settings

interface SettingsInteractor {
    fun getSavedThemeNumber(): Int
    fun updateSavedThemeNumber(themeNumber: Int)
}