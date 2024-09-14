package com.alchemtech.playlistmaker.domain.db

import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow

interface FavoriteTracksRepository {
    suspend fun addToFavoriteList(track: Track)
    suspend fun removeFromFavoriteList(track: Track)
    fun getFavoriteTrackList(): Flow<List<Track>>
    fun getAllTrackList(): Flow<List<Track>>
    suspend fun getTrackByID(id: String): Track
}