package com.alchemtech.playlistmaker.creators

import android.app.Application
import com.alchemtech.playlistmaker.data.network.RetrofitNetworkClient
import com.alchemtech.playlistmaker.data.repository.TracksRepositoryImpl
import com.alchemtech.playlistmaker.domain.api.TracksInteractor
import com.alchemtech.playlistmaker.domain.api.TracksRepository
import com.alchemtech.playlistmaker.domain.impl.TracksInteractorImpl

object SearchCreator {

    private lateinit var applicationContext: Application

    fun setApplicationContext(application: Application) {
        applicationContext = application
    }
    private fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient(applicationContext))
    }

    fun provideTracksInteractor(): TracksInteractor {
        return TracksInteractorImpl(getTracksRepository())
    }
}