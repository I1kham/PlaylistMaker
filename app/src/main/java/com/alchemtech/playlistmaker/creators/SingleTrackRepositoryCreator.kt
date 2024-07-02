package com.alchemtech.playlistmaker.creators

import android.app.Application
import com.alchemtech.playlistmaker.data.repository.SharedHistoryRepositoryImpl
import com.alchemtech.playlistmaker.domain.api.HistoryRepository
import com.alchemtech.playlistmaker.domain.api.SingleTrackInteractor
import com.alchemtech.playlistmaker.domain.impl.SingleTrackInteractorImpl

object SingleTrackRepositoryCreator {
    private lateinit var applicationContext: Application
    fun setApplicationContext(application: Application) {
        applicationContext = application
    }

    fun provideSingleTrackDb(): SingleTrackInteractor {
        return SingleTrackInteractorImpl(provideHistoryRepository())
    }

    private fun provideHistoryRepository(): HistoryRepository {
        return SharedHistoryRepositoryImpl(applicationContext)
    }
}