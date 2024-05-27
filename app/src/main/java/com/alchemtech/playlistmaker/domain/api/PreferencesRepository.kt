package com.alchemtech.playlistmaker.domain.api

import android.content.Context
import com.alchemtech.playlistmaker.data.dto.trackDto.TrackDto
import java.io.Serializable

interface PreferencesRepository {
    fun getSavedPref(name:String, key : String, context: Context): List<TrackDto>?
    fun setSavedPref(name:String, key : String, objects: Serializable, context: Context)

}