package com.alchemtech.playlistmaker.data.repository

import com.alchemtech.playlistmaker.data.dto.trackDto.TrackDto
import java.io.Serializable

interface HistoryRepository/*DbRepository*/ { // TODO:
    fun getSavedPref(): List<TrackDto>?
    fun setSavedPref( objects: Serializable)

}