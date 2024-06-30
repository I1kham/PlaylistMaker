package com.alchemtech.playlistmaker.creators

import com.alchemtech.playlistmaker.domain.settings.SettingsInteractor
import com.alchemtech.playlistmaker.domain.settings.impl.SettingsInteractorImpl

object SettingsInteractorCreator {
    fun provideSettingsInteractor(): SettingsInteractor {
        return SettingsInteractorImpl()
    }
}