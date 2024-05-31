package com.alchemtech.playlistmaker.domain.api

import com.alchemtech.playlistmaker.domain.entity.Track
import java.io.Serializable

interface HistoryRepository {
    fun getSavedPref(): List<Track>?
    fun setSavedPref( objects: Serializable)

   fun setNameKey(name: String, key: String)
}