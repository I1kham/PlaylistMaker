package com.alchemtech.playlistmaker.domain.api

import android.content.Context
import com.alchemtech.playlistmaker.data.dto.trackDto.TrackDto
import com.alchemtech.playlistmaker.domain.entity.Track
import java.io.Serializable

interface PreferencesRepository {
    fun getSavedPref(name:String, key : String, context: Context): List<TrackDto>?
    fun setSavedPref(name:String, key : String, objects: Serializable, context: Context)
    fun addTrack( track: Track)
    fun clearTracksList()
    fun getTrackList() : List<Track>
    fun writeTrackListToDb()

    fun readTrackListFromDb()
}