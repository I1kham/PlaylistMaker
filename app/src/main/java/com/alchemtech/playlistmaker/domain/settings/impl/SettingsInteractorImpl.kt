package com.alchemtech.playlistmaker.domain.settings.impl

import com.alchemtech.playlistmaker.domain.settings.SettingsInteractor
import com.alchemtech.playlistmaker.domain.settings.SettingsRepository

class SettingsInteractorImpl(val settingsRepository: SettingsRepository) : SettingsInteractor{
    override fun getSavedThemeNumber(): Int {
        return  settingsRepository.getThemeSettings()
    }

    override fun updateSavedThemeNumber(themeNumber: Int) {
        settingsRepository.updateThemeSetting(themeNumber)
    }
}