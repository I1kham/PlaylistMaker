package com.alchemtech.playlistmaker.creators

import android.app.Application
import com.alchemtech.playlistmaker.data.network.CheckInternetConnectionRepositoryImpl
import com.alchemtech.playlistmaker.domain.api.CheckConnectionInteractor
import com.alchemtech.playlistmaker.domain.api.CheckInternetConnectionRepository
import com.alchemtech.playlistmaker.domain.impl.CheckConnectionInteractorImpl

object InternetCheckCreator {
    private lateinit var applicationContext: Application

    fun setApplicationContext(application: Application) {
        applicationContext = application
    }

    fun provideInternetCheck(): CheckConnectionInteractor {
        return CheckConnectionInteractorImpl(provideCheckInternetRepository())

    }

    private fun provideCheckInternetRepository(): CheckInternetConnectionRepository {
        return CheckInternetConnectionRepositoryImpl(applicationContext)
    }
}