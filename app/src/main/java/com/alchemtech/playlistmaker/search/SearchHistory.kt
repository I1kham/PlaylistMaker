package com.alchemtech.playlistmaker.search

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.alchemtech.playlistmaker.track.Track
import com.google.gson.Gson

const val MAX_HISTORY_LIST_SIZE = 10
const val SAVED_TRACKS = ""

class SearchHistory : AppCompatActivity() {

    fun getHistoryListFromSharePreferences(sharedPreferences: SharedPreferences): MutableList<Track> {

        try {
            val json = sharedPreferences.getString(SAVED_TRACKS, null)
                ?: return null as MutableList<Track>
            println("Restoring")
            println(json) // todo

            return Gson().fromJson(json, Array<Track>::class.java).toMutableList()
        }
        catch (e : NullPointerException){
            return mutableListOf<Track>()
        }

    }


    fun setHistoryListToSharePreferences(
        sharedPreferences: SharedPreferences,
        list: MutableList<Track>,
    ) {
        val json = Gson().toJson(list)
        println("SAVING") //TODO
        println(json) // todo
        sharedPreferences.edit()
            .putString(SAVED_TRACKS, json)
            .apply()

    }

}