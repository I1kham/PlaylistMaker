package com.alchemtech.playlistmaker.domain.impl

import com.alchemtech.playlistmaker.domain.db.FavoriteTracksRepository
import com.alchemtech.playlistmaker.domain.db.TracksDbInteractor
import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow

class TracksDbInteractorImpl(
    private val favoriteTracksRepository: FavoriteTracksRepository,
) : TracksDbInteractor {
    override suspend fun addToFavoriteList(track: Track) {
        favoriteTracksRepository.addToFavoriteList(track)
    }

    override suspend fun removeFromFavoriteList(track: Track) {
        favoriteTracksRepository.removeFromFavoriteList(track)
    }

    override  fun getFavoriteTrackList(): Flow<List<Track>> {
        return favoriteTracksRepository.getFavoriteTrackList()
    }

    override  fun getAllTrackList(): Flow<List<Track>> {
        return favoriteTracksRepository.getAllTrackList()
    }

    override suspend fun getTrackById(id: String): Track {
       return favoriteTracksRepository.getTrackByID(id)
    }
}