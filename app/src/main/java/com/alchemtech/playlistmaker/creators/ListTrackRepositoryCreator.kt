package com.alchemtech.playlistmaker.creators

import android.content.Context
import com.alchemtech.playlistmaker.data.repository.SharedHistoryRepositoryImpl
import com.alchemtech.playlistmaker.domain.api.HistoryRepository
import com.alchemtech.playlistmaker.domain.api.TrackHistoryInteractor
import com.alchemtech.playlistmaker.domain.impl.TrackHistoryInteractorImpl

object ListTrackRepositoryCreator {
    fun provideListTrackDb(context: Context): TrackHistoryInteractor {
        return TrackHistoryInteractorImpl(provideHistoryRepository(context))
    }

    private fun provideHistoryRepository(context: Context):HistoryRepository{
        return SharedHistoryRepositoryImpl(context)
    }
}