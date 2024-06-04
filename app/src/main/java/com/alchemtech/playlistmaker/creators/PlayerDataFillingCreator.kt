package com.alchemtech.playlistmaker.creators

import android.app.Application
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.presenters.PlayerFilling
import com.alchemtech.playlistmaker.presentation.presenters.PlayerFillingImpl

object PlayerDataFillingCreator {
    private lateinit var applicationContext: Application
    fun setApplicationContext(application: Application) {
        applicationContext = application
    }

    fun provide( binding: ActivityPlayerBinding, track: Track): PlayerFilling {
        return PlayerFillingImpl(track, binding, applicationContext)
    }
}