package com.alchemtech.playlistmaker.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.alchemtech.playlistmaker.data.dto.trackDto.TrackDto
import com.alchemtech.playlistmaker.domain.api.HistoryRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

class SharedHistoryRepositoryImpl :
    HistoryRepository {
    private var name: String? = null
    private var key: String? = null
    private var context: Context? = null


   override fun setValues(name: String, key: String, context: Context) {
        this.name = name
        this.key = key
        this.context = context
    }

    override fun getSavedPref(): List<TrackDto>? {

        val shared = context!!.getSharedPreferences(/* name = */ name, /* mode = */ MODE_PRIVATE)
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

    override fun setSavedPref(objects: Serializable) {
        val json = Gson().toJson(objects)

        context!!.getSharedPreferences(name, MODE_PRIVATE).edit()
            .putString(/* key = */ key, /* value = */ json)
            .apply()
    }
}