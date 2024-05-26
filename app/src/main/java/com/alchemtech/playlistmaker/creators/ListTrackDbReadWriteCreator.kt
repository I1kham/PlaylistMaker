package com.alchemtech.playlistmaker.creators

import android.content.Context
import com.alchemtech.playlistmaker.domain.db.ListTrackDbInteractor

object ListTrackDbReadWriteCreator  {

    fun provideListTrackDb(context: Context): ListTrackDbInteractor {
        return ListTrackDbInteractor(context = context)

    }

}