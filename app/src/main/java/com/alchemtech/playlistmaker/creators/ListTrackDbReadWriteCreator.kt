package com.alchemtech.playlistmaker.creators

import android.content.Context
import com.alchemtech.playlistmaker.domain.db.ListTrackDbUseCase

object ListTrackDbReadWriteCreator  {

    fun provideListTrackDb(context: Context): ListTrackDbUseCase {
        return ListTrackDbUseCase(context = context)

    }

}