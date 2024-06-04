package com.alchemtech.playlistmaker.domain.api

import com.alchemtech.playlistmaker.domain.entity.Track

interface TrackHistoryInteractor {

    fun addTrack(track: Track)
    fun clearTracksList()
    fun getTrackList(): List<Track>
    fun writeTrackList()
    fun readTrackList()
}