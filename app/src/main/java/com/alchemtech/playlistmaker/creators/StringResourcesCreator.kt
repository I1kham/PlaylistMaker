package com.alchemtech.playlistmaker.creators

import android.app.Application
import com.alchemtech.playlistmaker.domain.api.StringResources

object StringResourcesCreator {
    private lateinit var applicationContext: Application
    fun setApplicationContext(application: Application) {
        applicationContext = application
    }

    fun consume(): StringResources {
        return StringResources.provide(
            applicationContext
        )
    }
}