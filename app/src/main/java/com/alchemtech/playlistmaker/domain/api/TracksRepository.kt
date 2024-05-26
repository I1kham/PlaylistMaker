package com.alchemtech.playlistmaker.domain.api

import com.alchemtech.playlistmaker.domain.entity.Track

interface TracksRepository {
    fun searchTracks(expression: String): List<Track>

}