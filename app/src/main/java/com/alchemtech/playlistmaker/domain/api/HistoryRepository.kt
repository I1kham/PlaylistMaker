package com.alchemtech.playlistmaker.domain.api

import com.alchemtech.playlistmaker.domain.entity.Track

interface HistoryRepository {
    fun getSavedPref(): List<Track>?
    fun setSavedPref(objects: Any)

   fun setNameKey(name: String, key: String)
}