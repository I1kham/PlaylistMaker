package com.alchemtech.playlistmaker.creators

import android.content.Context
import com.alchemtech.playlistmaker.data.repository.SharedPreferencesRepositoryImpl
import com.alchemtech.playlistmaker.domain.api.PreferencesRepository
import com.alchemtech.playlistmaker.domain.db.ListTrackDbUseCase

object ListTrackDbReadWriteCreator  {

    fun provideListTrackDb(context: Context): PreferencesRepository {
        return ListTrackDbUseCase(context = context)

    }

}