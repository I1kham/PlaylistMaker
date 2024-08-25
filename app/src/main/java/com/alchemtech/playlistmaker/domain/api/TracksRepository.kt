package com.alchemtech.playlistmaker.domain.api

import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.util.Resource
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    fun searchTracks(expression: String): Flow<Resource<List<Track>>>
}