package com.alchemtech.playlistmaker.domain

import android.content.Context
import com.alchemtech.playlistmaker.data.dto.sharePreferences.SharedPrefImpl
import com.alchemtech.playlistmaker.domain.models.Track
import java.io.Serializable

interface TrackListHistory : SharedPrefImpl{
//
//        val listHistory = mutableListOf<Track>()
//
//
    fun readTracksList(context: Context): List<Track> {

        val dto = getSavedPref(name = SAVED_TRACKS, key = SAVED_LIST, context = context)

        if (dto.isNullOrEmpty()) {
            return emptyList()
        } else {
            return dto.map {
                Track(
                    it.trackName,
                    it.artistName,
                    it.trackTimeMillis,
                    it.artworkUrl100,
                    it.trackId,
                    it.collectionName,
                    it.releaseDate,
                    it.primaryGenreName,
                    it.country,
                    it.previewUrl,
                )
            }
        }
    }

    fun writeTrackList(list: MutableList<Track>, context: Context) {
        val tracks = list as List<Track>
        setSavedPref(
            name = SAVED_TRACKS,
            key = SAVED_LIST,
            objects = tracks as Serializable,
            context = context
        )
//        fun addTrack(track: Track): List<Track> {
//
//            listHistory.remove(track)
//            if (listHistory.isEmpty()) {
//                listHistory.add(track)
//            } else {
//
//                if (listHistory.size < MAX_HISTORY_LIST_SIZE) {
//                    listHistory.add(0, track)
//
//                } else {
//                    (listHistory ).removeLast()
//                    listHistory.add(0, track)
//                }
//            }
//            return emptyList()
//        }
    }

    companion object {
        const val SAVED_TRACKS = "SAVED_TRACKS"
        const val SAVED_LIST = "SAVED_LIST"
        const val MAX_HISTORY_LIST_SIZE = 10
    }
}
