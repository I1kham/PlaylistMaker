package com.alchemtech.playlistmaker.domain

import android.content.Context
import com.alchemtech.playlistmaker.domain.models.Track

object HistoryCreator : TrackListHistory {
    private val listHistory = mutableListOf<Track>()
    private const val MAX_HISTORY_LIST_SIZE = 10

    fun addTrack(track: Track): List<Track> {

        listHistory.remove(track)
        if (listHistory.isEmpty()) {
            listHistory.add(track)
        } else {

            if (listHistory.size < MAX_HISTORY_LIST_SIZE) {
                listHistory.add(0, track)
            } else {
                (listHistory).removeLast()
                listHistory.add(0, track)
            }
        }
        return emptyList()
    }

    fun addList(list: List<Track>) {
        listHistory.clear()
        listHistory.map { list }
    }

    fun readTrackListFromDb(context: Context) {
        listHistory.clear()
        listHistory.addAll(readTracksList(context))
    }

    fun writeTrackListToDb(context: Context) {
        writeTrackList(listHistory, context)
    }

    fun getTrackList():  List<Track> { 
        return listHistory
    }

    fun clearTracksList() {
        listHistory.clear()
    }
}