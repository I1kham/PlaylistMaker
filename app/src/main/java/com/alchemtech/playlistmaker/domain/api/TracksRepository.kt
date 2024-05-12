package com.alchemtech.playlistmaker.domain.api

import com.alchemtech.playlistmaker.domain.models.Track

interface TracksRepository {
    fun searchTracks(expression: String): List<Track>
}