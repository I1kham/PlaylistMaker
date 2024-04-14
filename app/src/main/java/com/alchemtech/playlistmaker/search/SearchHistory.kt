package com.alchemtech.playlistmaker.search

import android.content.ClipData
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.alchemtech.playlistmaker.track.Track
import com.google.gson.Gson

const val SAVED_TRACKS = ""
const val MAX_HISTORY_LIST_SIZE = 10
var historyList = mutableListOf<Track>(
//    Track(
////            "Smells Like Teen Spirit",
//        "012345678912345678901234567890123456789",
//        "1234567890123456789",
//        1_000_000_000,
//        "https://i1s5-ssl.mzstatic.com/image/thumb/Music115/v4/7b/58/c2/7b58c21a-2b51-2bb2-e59a-9bb9b96ad8c3/00602567924166.rgb.jpg/100x100bb.jpg",
//        ""
//    ),
//    Track(
//        "Billie Jean",
//        "Michael Jackson",
//        1_000_000_000,
//        "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/3d/9d/38/3d9d3811-71f0-3a0e-1ada-3004e56ff852/827969428726.jpg/100x100bb.jpg",
//        ""
//    )
////todo
)

class SearchHistory : AppCompatActivity() {


    fun addTrackToHistoryList(track: Track) {

        historyList.remove(track)
        println("5555555555555555555555555")
        println(track.artworkUrl100)
        println(historyList.lastIndex)
        if (historyList.lastIndex < MAX_HISTORY_LIST_SIZE - 1 ) {
            historyList.add(track)
            println(track.artworkUrl100)
        }

    }


    fun getHistoryListFromSharePreferences(sharedPreferences: SharedPreferences): MutableList<Track> {
        val json = sharedPreferences.getString(SAVED_TRACKS, "")
            ?: return emptyList<Track>() as MutableList<Track>
        return Gson().fromJson<List<ClipData.Item>>(json, List::class.java) as MutableList<Track>
    }


    fun setHistoryListToSharePreferences(
        sharedPreferences: SharedPreferences,
        list: MutableList<Track>,
    ) {
        val json = Gson().toJson(list)
        println(json)
        sharedPreferences.edit()
            .putString(SAVED_TRACKS, json)
            .apply()

    }

}