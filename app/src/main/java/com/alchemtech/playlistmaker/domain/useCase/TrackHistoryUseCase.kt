package com.alchemtech.playlistmaker.domain.useCase

import com.alchemtech.playlistmaker.domain.entity.Track

interface TrackHistoryUseCase {

    fun addTrack(track: Track)
    fun clearTracksList()
    fun getTrackList(): List<Track>
    fun writeTrackListToDb()
    fun readTrackListFromDb()
}