package com.alchemtech.playlistmaker.domain

import android.content.Context
import com.alchemtech.playlistmaker.domain.models.Track

object TEEEEST : TrackListHistory {

//private val history= History()

    fun getTrackList(context: Context): List<Track> {
        return readTracksList(context)
    }

    fun setTrackList( context: Context) {
//        writeTrackList(history.listHistory, context)
    }
}
