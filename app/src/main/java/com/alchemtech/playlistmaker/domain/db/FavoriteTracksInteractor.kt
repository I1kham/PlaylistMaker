package com.alchemtech.playlistmaker.domain.db

import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow

interface FavoriteTracksInteractor {
    suspend fun addToFavoriteList(track: Track)
    suspend fun removeFromFavoriteList(track: Track)
    suspend fun getFavoriteTrackList(): Flow<List<Track>>
}