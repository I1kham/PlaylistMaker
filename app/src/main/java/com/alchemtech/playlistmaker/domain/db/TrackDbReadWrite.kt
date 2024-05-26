package com.alchemtech.playlistmaker.domain.db

import android.content.Context
import com.alchemtech.playlistmaker.data.dto.repository.SharedPrefImpl
import com.alchemtech.playlistmaker.domain.entity.Track
import java.io.Serializable

interface TrackDbReadWrite : SharedPrefImpl {
    fun readTRack(context: Context): Track? {

        val a = getSavedPref(SAVED_TRACK, SAVED_TRACK, context)?.map {
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
        return a?.get(a.lastIndex)
    }

    fun writeTrack(track: Track, context: Context) {
        val list = listOf<Track>(track)


        setSavedPref(
            name = SAVED_TRACK,
            key = SAVED_TRACK,
            objects = list as Serializable,
            context = context
        )

    }
    companion object {
      private const val SAVED_TRACK = "SAVED_TRACK"
    }
}