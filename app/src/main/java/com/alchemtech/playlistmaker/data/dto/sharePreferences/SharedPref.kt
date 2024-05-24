package com.alchemtech.playlistmaker.data.dto.sharePreferences

import android.content.Context
import com.alchemtech.playlistmaker.data.dto.trackDto.TrackDto
import java.io.Serializable

interface SharedPref {
    fun getSavedPref(name:String, key : String, context: Context): List<TrackDto>?
    fun setSavedPref(name:String, key : String, objects: Serializable, context: Context)

}