package com.alchemtech.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import com.alchemtech.playlistmaker.domain.settings.SettingsInteractor
import com.alchemtech.playlistmaker.presentation.mainActivityModule
import com.alchemtech.playlistmaker.presentation.mediaLibViewModel
import com.alchemtech.playlistmaker.presentation.playerViewModel
import com.alchemtech.playlistmaker.presentation.settingsActivityModule
import com.alchemtech.playlistmaker.presentation.tracksActivityViewModel
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                /*domain*/

                /*ViewModel*/
                mainActivityModule,
                settingsActivityModule,
                tracksActivityViewModel,
                playerViewModel,
                mediaLibViewModel
            )
        }
        switchTheme()
    }

    private fun switchTheme() {
        val settingsInteractor: SettingsInteractor by inject()
        setDefaultNightMode(
            settingsInteractor.getThemeSettings().themeNumber
        )
    }
}