package com.alchemtech.playlistmaker.domain.api

import com.alchemtech.playlistmaker.domain.TracksResponseContainer

interface TracksRepository {
    fun searchTracks(expression: String): TracksResponseContainer

}