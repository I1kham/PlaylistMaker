package com.alchemtech.playlistmaker.search

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.alchemtech.playlistmaker.track.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

const val MAX_HISTORY_LIST_SIZE = 10
const val SAVED_TRACKS = ""

class SearchHistory : AppCompatActivity() {

    @Suppress("UNCHECKED_CAST")
    fun getHistoryListFromSharePreferences(sharedPreferences: SharedPreferences): MutableList<Track> {


        val json =
            sharedPreferences.getString(/* key = */ SAVED_TRACKS, /* defValue = */ null)
                ?: return mutableListOf()
        return Gson().fromJson<Any>(
            json,
            object : TypeToken<MutableList<Track?>?>() {}.type
        ) as MutableList<Track>

    }


    fun setHistoryListToSharePreferences(
        sharedPreferences: SharedPreferences,
        list: MutableList<Track>,
    ) {
        val json = Gson().toJson(list)
        sharedPreferences.edit()
            .putString(SAVED_TRACKS, json)
            .apply()
    }

    fun addTrackToList(track: Track) {

        var historyList = getHistoryListFromSharePreferences(
            getSharedPreferences(
                SAVED_TRACKS,
                MODE_PRIVATE
            )
        )
        historyList.remove(track)
        if (historyList.isEmpty()) {
            historyList.add(track)
        } else {
            if (historyList.size < MAX_HISTORY_LIST_SIZE) {
                historyList.add(0, track)

            } else {
                historyList.removeLast()
                historyList.add(0, track)
            }
        }

        setHistoryListToSharePreferences(
            getSharedPreferences(
                SAVED_TRACKS,
                MODE_PRIVATE
            ), historyList
        )

    }


}