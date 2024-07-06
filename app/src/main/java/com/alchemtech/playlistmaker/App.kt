package com.alchemtech.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import com.alchemtech.playlistmaker.creators.ThemeInteractorCreator
import com.alchemtech.playlistmaker.presentation.mainModule
import com.alchemtech.playlistmaker.presentation.settingsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        switchTheme()

        startKoin {
            androidContext(this@App)
            modules(mainModule,settingsModule)
        }
    }

    private fun switchTheme() {
        setDefaultNightMode(
            ThemeInteractorCreator
                .provideThemeInteractor(this)
                .getThemeSettings().themeNumber
        )
    }
}