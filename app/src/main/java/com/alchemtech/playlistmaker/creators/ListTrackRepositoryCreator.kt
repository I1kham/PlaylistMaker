package com.alchemtech.playlistmaker.creators

import android.app.Application
import com.alchemtech.playlistmaker.data.repository.SharedHistoryRepositoryImpl
import com.alchemtech.playlistmaker.domain.api.HistoryRepository
import com.alchemtech.playlistmaker.domain.api.TrackHistoryInteractor
import com.alchemtech.playlistmaker.domain.impl.TrackHistoryInteractorImpl

object ListTrackRepositoryCreator {
    private lateinit var applicationContext: Application
    fun setApplicationContext(application: Application) {
        applicationContext = application
    }

    fun provideListTrackDb(): TrackHistoryInteractor {
        return TrackHistoryInteractorImpl(provideHistoryRepository())
    }

    private fun provideHistoryRepository(): HistoryRepository {
        return SharedHistoryRepositoryImpl(applicationContext)
    }
}