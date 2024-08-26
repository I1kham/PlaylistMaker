package com.alchemtech.playlistmaker.domain.impl

import com.alchemtech.playlistmaker.domain.db.FavoriteTracksInteractor
import com.alchemtech.playlistmaker.domain.db.FavoriteTracksRepository
import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow

class FavoriteTracksInteractorImpl(
    private val favoriteTracksRepository: FavoriteTracksRepository,
) : FavoriteTracksInteractor {
    override suspend fun addToFavoriteList(track: Track) {
        favoriteTracksRepository.addToFavoriteList(track)
    }

    override suspend fun removeFromFavoriteList(track: Track) {
        favoriteTracksRepository.removeFromFavoriteList(track)
    }

    override suspend fun getFavoriteTrackList(): Flow<List<Track>> {
        return favoriteTracksRepository.getFavoriteTrackList()
    }
}