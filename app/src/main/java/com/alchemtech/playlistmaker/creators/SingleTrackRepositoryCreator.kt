package com.alchemtech.playlistmaker.creators

import android.content.Context
import com.alchemtech.playlistmaker.data.repository.SharedHistoryRepositoryImpl
import com.alchemtech.playlistmaker.domain.api.HistoryRepository
import com.alchemtech.playlistmaker.domain.api.SingleTrackInteractor
import com.alchemtech.playlistmaker.domain.impl.SingleTrackInteractorImpl

object SingleTrackRepositoryCreator {

    fun provideSingleTrackDb(context: Context): SingleTrackInteractor {
        return SingleTrackInteractorImpl(provideHistoryRepository(context))
    }

    private fun provideHistoryRepository(context: Context): HistoryRepository {
        return SharedHistoryRepositoryImpl(context)
    }
}