package com.alchemtech.playlistmaker.creators

import android.content.Context
import com.alchemtech.playlistmaker.data.repository.HistoryRepository
import com.alchemtech.playlistmaker.data.repository.SharedHistoryRepositoryImpl

object HistoryRepositoryCreator {

    fun provideHistoryRepository(name: String, key: String, context: Context): HistoryRepository {
        return SharedHistoryRepositoryImpl(name, key, context)
    }
}