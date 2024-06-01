package com.alchemtech.playlistmaker.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.alchemtech.playlistmaker.domain.api.HistoryRepository
import com.alchemtech.playlistmaker.domain.entity.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

class SharedHistoryRepositoryImpl(private var context: Context) :
    HistoryRepository {
    private var name: String? = null
    private var key: String? = null
   // private var context: Context? = null


   override fun setNameKey(name: String, key: String) {
        this.name = name
        this.key = key
       // this.context = context
    }

    override fun getSavedPref(): List<Track> {

        val shared = context.getSharedPreferences(/* name = */ name, /* mode = */ MODE_PRIVATE)
        val json =
            shared
                .getString(/* key = */ key,
                    /* defValue = */ null
                )
                ?: return emptyList()
//        println("empty")
        return Gson().fromJson<Track>(
            json,
            object : TypeToken<List<Track?>?>
                () {}.type
        ) as List<Track>
    }

    override fun setSavedPref(objects: Serializable) {
        val json = Gson().toJson(objects)

        context.getSharedPreferences(name, MODE_PRIVATE).edit()
            .putString(/* key = */ key, /* value = */ json)
            .apply()
    }
}