package com.alchemtech.playlistmaker.domain.api

import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow

interface TracksInteractor {
    fun searchTracks(expression: String/*, consumer: TracksConsumer*/): Flow<Pair<List<Track>?, Int?>>
}