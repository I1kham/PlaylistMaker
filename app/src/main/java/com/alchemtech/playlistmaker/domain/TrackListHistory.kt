package com.alchemtech.playlistmaker.domain

import android.content.Context
import com.alchemtech.playlistmaker.data.dto.sharePreferences.SharedPrefImpl
import com.alchemtech.playlistmaker.domain.models.Track
import java.io.Serializable

interface TrackListHistory : SharedPrefImpl {

    fun readTracksList(context: Context): List<Track> {
        println("==============================")
        val dto = getSavedPref(name = SAVED_TRACKS, key = SAVED_LIST, context = context)
        println("+++++++++++++++++++++++++++++++++")
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

    }

    companion object {
        const val SAVED_TRACKS = "SAVED_TRACKS"
        const val SAVED_LIST = "SAVED_LIST"
    }
}