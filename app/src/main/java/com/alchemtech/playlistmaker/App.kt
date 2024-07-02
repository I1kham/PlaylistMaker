package com.alchemtech.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import com.alchemtech.playlistmaker.creators.ExternalCreator
import com.alchemtech.playlistmaker.creators.ListTrackRepositoryCreator
import com.alchemtech.playlistmaker.creators.MoveToActivityCreator
import com.alchemtech.playlistmaker.creators.PlayerDataFillingCreator
import com.alchemtech.playlistmaker.creators.SearchCreator
import com.alchemtech.playlistmaker.creators.SingleTrackRepositoryCreator
import com.alchemtech.playlistmaker.creators.ThemeInteractorCreator
import com.alchemtech.playlistmaker.domain.settings.SettingsInteractor

class App : Application() {
    lateinit var settingsInteractor: SettingsInteractor
    override fun onCreate() {
        super.onCreate()
        ListTrackRepositoryCreator.setApplicationContext(this)
        PlayerDataFillingCreator.setApplicationContext(this)
        SearchCreator.setApplicationContext(this)
        ExternalCreator.setApplicationContext(this)
        ThemeInteractorCreator.setApplicationContext(this)
        MoveToActivityCreator.setApplicationContext(this)
        SingleTrackRepositoryCreator.setApplicationContext(this)
        settingsInteractor = ThemeInteractorCreator.provideThemeInteractor()
        switchTheme()
    }

    private fun switchTheme() {
        setDefaultNightMode(
            settingsInteractor.getThemeSettings().themeNumber
        )
    }
}