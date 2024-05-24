package com.alchemtech.playlistmaker.domain

import android.content.Context
import com.alchemtech.playlistmaker.domain.models.Track

object HistoryListCreator : TrackListHistory {
    fun get(context: Context): List<Track> {
        return readTracksList( context)
    }

    fun set(list: MutableList<Track>, context: Context) {
        writeTrackList(list, context)
    }
}