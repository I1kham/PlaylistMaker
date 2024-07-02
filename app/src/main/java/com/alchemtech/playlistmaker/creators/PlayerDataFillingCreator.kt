package com.alchemtech.playlistmaker.creators

import android.app.Application
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.presentation.presenters.PlayerFilling
import com.alchemtech.playlistmaker.presentation.presenters.PlayerFillingImpl

object PlayerDataFillingCreator {
    private lateinit var applicationContext: Application
    fun setApplicationContext(application: Application) {
        applicationContext = application
    }

    fun provide( binding: ActivityPlayerBinding): PlayerFilling {
        return PlayerFillingImpl( binding, applicationContext)
    }
}