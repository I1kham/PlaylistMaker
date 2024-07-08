package com.alchemtech.playlistmaker.domain.api

import com.alchemtech.playlistmaker.domain.entity.Track

interface HistoryRepository {
    fun getSavedTracks(): List<Track>?
    fun setTracksToSave(objects: Any)
}