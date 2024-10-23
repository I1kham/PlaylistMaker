package com.alchemtech.playlistmaker.data.db.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy. IGNORE)
    suspend fun addTrack(tracks: TrackEntity)

    @Query("DELETE FROM tracks_table WHERE trackId = :trackId")
    suspend fun deleteTrack(trackId: String)

    @Query("UPDATE tracks_table SET isFavorite = 0  WHERE trackId = :trackId")
    suspend fun unLikeTrack(trackId: String)

    @Query("SELECT * FROM tracks_table")
    fun getAllTracks(): Flow<List<TrackEntity>>

    @Query("SELECT * FROM tracks_table WHERE isFavorite = 1")
    fun getFavoriteTracks(): Flow<List<TrackEntity>>

    @Query("SELECT trackId FROM tracks_table")
    suspend fun getIdFavoriteTracks(): List<String>

    @Query("SELECT * FROM tracks_table WHERE trackId=:id")
    suspend fun getTrackByID(id: String): TrackEntity
}