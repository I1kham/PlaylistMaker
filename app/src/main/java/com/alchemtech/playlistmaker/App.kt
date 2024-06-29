package com.alchemtech.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import com.alchemtech.playlistmaker.creators.ListTrackRepositoryCreator
import com.alchemtech.playlistmaker.creators.PlayerDataFillingCreator
import com.alchemtech.playlistmaker.creators.SearchCreator

const val DARK_THEME = 1

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        switchTheme()


        ListTrackRepositoryCreator.setApplicationContext(this)
        PlayerDataFillingCreator.setApplicationContext(this)
        SearchCreator.setApplicationContext(this)
    }

    private fun switchTheme() {
        setDefaultNightMode(
            this.getSharedPreferences(/* name = */ DARK_THEME.toString(), /* mode = */ MODE_PRIVATE)
                .getInt(DARK_THEME.toString(), 1)
        )

    }
}