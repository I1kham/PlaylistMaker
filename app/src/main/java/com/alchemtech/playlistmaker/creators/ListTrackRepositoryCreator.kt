package com.alchemtech.playlistmaker.creators

import android.content.Context
import com.alchemtech.playlistmaker.data.repository.SharedHistoryRepositoryImpl
import com.alchemtech.playlistmaker.domain.api.HistoryRepository
import com.alchemtech.playlistmaker.domain.useCase.TrackHistoryUseCase
import com.alchemtech.playlistmaker.domain.useCase.TrackHistoryUseCaseImpl

object ListTrackRepositoryCreator {
    fun provideListTrackDb(context: Context): TrackHistoryUseCase {
        return TrackHistoryUseCaseImpl(context = context, provideHistoryRepository())
    }

    private fun provideHistoryRepository():HistoryRepository{
        return SharedHistoryRepositoryImpl()
    }
}