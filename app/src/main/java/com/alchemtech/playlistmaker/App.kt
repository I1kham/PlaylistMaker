package com.alchemtech.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import com.alchemtech.playlistmaker.creators.ExternalCreator
import com.alchemtech.playlistmaker.creators.ListTrackRepositoryCreator
import com.alchemtech.playlistmaker.creators.PlayerDataFillingCreator
import com.alchemtech.playlistmaker.creators.SearchCreator
import com.alchemtech.playlistmaker.creators.SettingsInteractorCreator
import com.alchemtech.playlistmaker.domain.settings.SettingsInteractor

class App : Application() {
    lateinit var settingsInteractor : SettingsInteractor
    override fun onCreate() {
        super.onCreate()
        ListTrackRepositoryCreator.setApplicationContext(this)
        PlayerDataFillingCreator.setApplicationContext(this)
        SearchCreator.setApplicationContext(this)
        ExternalCreator.setApplicationContext(this)
        SettingsInteractorCreator.setApplicationContext(this)
        settingsInteractor = SettingsInteractorCreator.provideSettingsInteractor()
        switchTheme()
    }

    private fun switchTheme() {
        setDefaultNightMode(
           settingsInteractor.getThemeSettings().themeNumber
        )
    }
}