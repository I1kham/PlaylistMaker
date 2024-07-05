package com.alchemtech.playlistmaker.domain.settings.impl

import com.alchemtech.playlistmaker.domain.settings.SettingsRepository
import com.alchemtech.playlistmaker.domain.settings.SettingsInteractor
import com.alchemtech.playlistmaker.domain.settings.model.ThemeSettings

class SettingsInteractorImpl(val settingsRepository: SettingsRepository) : SettingsInteractor{
    override fun getThemeSettings(): ThemeSettings {
        return  settingsRepository.getThemeSettings()
    }

    override fun updateThemeSetting(themeSettings: ThemeSettings) {
        settingsRepository.updateThemeSetting(themeSettings)
    }
}