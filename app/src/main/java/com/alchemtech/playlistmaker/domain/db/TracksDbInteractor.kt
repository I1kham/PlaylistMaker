package com.alchemtech.playlistmaker.domain.db

import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow

interface TracksDbInteractor {
    suspend fun addToTrackDb(track: Track)
    suspend fun getTrackById(id: String): Track
    fun getFavoriteTrackList(): Flow<List<Track>>
    fun getAllTrackList(): Flow<List<Track>>
    suspend fun deleteTrack(trackId: String)
    suspend fun unLikeTrack(trackId: String)
}