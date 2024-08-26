package com.alchemtech.playlistmaker.data.db.entity
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// TODO:
@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTrack(tracks: TrackEntity)

    @Delete
    suspend fun removeTrack(track: TrackEntity)

    @Query("SELECT * FROM tracks_table")
     fun getAllTracks(): Flow<List<TrackEntity>> // TODO: del suspend

    @Query("SELECT trackId FROM tracks_table")
    suspend fun getIdFavoriteTracks(): List<String>
}