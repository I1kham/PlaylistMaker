package com.alchemtech.playlistmaker.data.repository

import android.content.SharedPreferences
import com.alchemtech.playlistmaker.domain.api.HistoryRepository
import com.alchemtech.playlistmaker.domain.entity.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

class SharedHistoryRepositoryImpl(
    private val key: String,
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson,
) :
    HistoryRepository {

    override fun getSavedTracks(): List<Track> {
        val json = sharedPreferences.getString(/* key = */ key,
            /* defValue = */ null
        )
            ?: return emptyList()
        return gson.fromJson<Track>(
            json,
            object : TypeToken<List<Track?>?>
                () {}.type
        ) as List<Track>
    }

    override fun setTracksToSave(objects: Any) {
        val json = gson.toJson(objects as Serializable)
        sharedPreferences.edit()
            .putString(/* key = */ key, /* value = */ json)
            .apply()
    }
}