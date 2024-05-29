package com.alchemtech.playlistmaker.creators

import android.content.Context
import com.alchemtech.playlistmaker.domain.useCase.TrackHistoryUseCase
import com.alchemtech.playlistmaker.domain.useCase.TrackHistoryUseCaseImpl

object ListTrackRepositoryCreator {
    fun provideListTrackDb(context: Context): TrackHistoryUseCase {
        return TrackHistoryUseCaseImpl(context = context)
    }
}