package com.alchemtech.playlistmaker.search

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.alchemtech.playlistmaker.track.Track
import com.google.gson.Gson

const val MAX_HISTORY_LIST_SIZE = 10
const val SAVED_TRACKS = ""

class SearchHistory : AppCompatActivity() {

    fun getHistoryListFromSharePreferences(sharedPreferences: SharedPreferences): ArrayList<Track> {


        val json = sharedPreferences.getString(SAVED_TRACKS, "null")
            ?: return ArrayList()
        return Gson().fromJson(json, Array<Track>::class.java).toList() as ArrayList

    }


    fun setHistoryListToSharePreferences(
        sharedPreferences: SharedPreferences,
        list: ArrayList<Track>,
    ) {
        val json = Gson().toJson(list)
        sharedPreferences.edit()
            .putString(SAVED_TRACKS, json)
            .apply()

    }

}