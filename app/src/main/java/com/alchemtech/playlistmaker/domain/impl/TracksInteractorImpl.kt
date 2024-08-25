package com.alchemtech.playlistmaker.domain.impl

import com.alchemtech.playlistmaker.domain.api.TracksInteractor
import com.alchemtech.playlistmaker.domain.api.TracksRepository
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TracksInteractorImpl(private val tracksRepository: TracksRepository) : TracksInteractor {

    override fun searchTracks(expression: String): Flow<Pair<List<Track>?, Int?>> {
        return tracksRepository.searchTracks(expression).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}