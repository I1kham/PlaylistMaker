package com.alchemtech.playlistmaker.creators

import android.app.Application
import com.alchemtech.playlistmaker.data.MoveToImpl
import com.alchemtech.playlistmaker.domain.MoveTo

object MoveToActivityCreator {
    private lateinit var applicationContext: Application

    fun setApplicationContext(application: Application) {
        applicationContext = application
    }
    fun provideMoveToActivity(): MoveTo {
        return MoveToImpl(applicationContext)
    }
}