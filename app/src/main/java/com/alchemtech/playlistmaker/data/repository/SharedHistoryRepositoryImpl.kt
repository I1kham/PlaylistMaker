package com.alchemtech.playlistmaker.data.repository

import android.content.SharedPreferences
import com.alchemtech.playlistmaker.data.converters.TracksStringConvertor
import com.alchemtech.playlistmaker.domain.api.HistoryRepository
import com.alchemtech.playlistmaker.domain.entity.Track

class SharedHistoryRepositoryImpl(
    private val key: String,
    private val sharedPreferences: SharedPreferences,
    private val tracksStringConvertor: TracksStringConvertor,
) :
    HistoryRepository {

    override fun getSavedTracks(): List<Track> {
        val json = sharedPreferences.getString(/* key = */ key,
            /* defValue = */ null
        )
             return tracksStringConvertor.map(json)
    }

    override fun setTracksToSave(tracksList: List<Track>) {
        sharedPreferences.edit()
            .putString(/* key = */ key, /* value = */ tracksStringConvertor.map(tracksList))
            .apply()
    }
}