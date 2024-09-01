package com.alchemtech.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import com.alchemtech.playlistmaker.di.favoriteTracksViewModel
import com.alchemtech.playlistmaker.di.mediaLibViewModel
import com.alchemtech.playlistmaker.di.playListsViewModel
import com.alchemtech.playlistmaker.di.playerViewModel
import com.alchemtech.playlistmaker.di.settingsActivityModule
import com.alchemtech.playlistmaker.di.startViewModel
import com.alchemtech.playlistmaker.di.tracksActivityViewModel
import com.alchemtech.playlistmaker.domain.settings.SettingsInteractor
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                /*ViewModel*/
                settingsActivityModule,
                tracksActivityViewModel,
                playerViewModel,
                mediaLibViewModel,
                startViewModel,
                /*Fragments*/
                favoriteTracksViewModel,
                playListsViewModel
            )
        }
        switchTheme()
    }

    private fun switchTheme() {
        val settingsInteractor: SettingsInteractor by inject()
        val theme = settingsInteractor.getSavedThemeNumber()
        if (theme != -1) {
            setDefaultNightMode(theme)
        }
    }
}