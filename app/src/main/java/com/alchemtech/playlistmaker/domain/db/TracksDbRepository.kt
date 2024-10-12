package com.alchemtech.playlistmaker.domain.db

import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow

interface TracksDbRepository {
    suspend fun addToTracksDb(track: Track)
    fun getFavoriteTrackList(): Flow<List<Track>>
    fun getAllTrackList(): Flow<List<Track>>
    suspend fun getTrackByID(id: String): Track

    // TODO: new
    suspend fun likeTRack(trackId:String)
    suspend fun deleteTrack(trackId: String)
    suspend fun unLikeTrack(trackId: String)
}