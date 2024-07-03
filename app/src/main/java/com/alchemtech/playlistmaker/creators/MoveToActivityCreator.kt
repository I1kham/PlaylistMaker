package com.alchemtech.playlistmaker.creators

import android.app.Application
import com.alchemtech.playlistmaker.data.NavigatorImpl
import com.alchemtech.playlistmaker.domain.Navigator

object MoveToActivityCreator {
    private lateinit var applicationContext: Application

    fun setApplicationContext(application: Application) {
        applicationContext = application
    }
    fun provideMoveToActivity(): Navigator {
        return NavigatorImpl(applicationContext)
    }
}