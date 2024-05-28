package com.alchemtech.playlistmaker.creators

import android.content.Context
import com.alchemtech.playlistmaker.domain.api.DbRepository
import com.alchemtech.playlistmaker.domain.db.ListTrackDbUseCase

object ListTrackDbReadWriteCreator {
    fun provideListTrackDb(context: Context): DbRepository {
        return ListTrackDbUseCase(context = context)
    }
}