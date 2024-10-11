package com.alchemtech.playlistmaker.domain.db

import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow

interface TracksDbInteractor {
    suspend fun addToFavoriteList(track: Track)
    suspend fun removeFromFavoriteList(track: Track)
    suspend fun getTrackById(id: String): Track
    fun getFavoriteTrackList(): Flow<List<Track>>
    fun getAllTrackList(): Flow<List<Track>>
}