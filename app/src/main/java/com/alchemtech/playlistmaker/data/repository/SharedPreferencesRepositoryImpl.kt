package com.alchemtech.playlistmaker.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.alchemtech.playlistmaker.data.dto.trackDto.TrackDto
import com.alchemtech.playlistmaker.domain.api.PreferencesRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

interface SharedPreferencesRepositoryImpl : PreferencesRepository {
    override fun getSavedPref(name: String, key: String, context: Context): List<TrackDto>? {

        val shared = context.getSharedPreferences(/* name = */ name, /* mode = */ MODE_PRIVATE)
        val json =
            shared
                .getString(/* key = */ key,
                    /* defValue = */ null
                )
                ?: return emptyList<TrackDto>()
        println("empty")
        return Gson().fromJson<TrackDto>(
            json,
            object : TypeToken<List<TrackDto?>?>
                () {}.type
        ) as List<TrackDto>?
    }

    override fun setSavedPref(name: String, key: String, objects: Serializable, context: Context) {
        val json = Gson().toJson(objects)

        context.getSharedPreferences(name, MODE_PRIVATE).edit()
            .putString(/* key = */ key, /* value = */ json)
            .apply()
    }
}