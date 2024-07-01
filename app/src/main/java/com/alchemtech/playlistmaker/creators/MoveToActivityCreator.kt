package com.alchemtech.playlistmaker.creators

import android.app.Application
import com.alchemtech.playlistmaker.data.MoveToActivityImpl
import com.alchemtech.playlistmaker.domain.MoveToActivity

object MoveToActivityCreator {
    private lateinit var applicationContext: Application

    fun setApplicationContext(application: Application) {
        applicationContext = application
    }
    fun provideMoveToActivity(): MoveToActivity {
        return MoveToActivityImpl(applicationContext)
    }
}