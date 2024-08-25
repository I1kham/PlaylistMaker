package com.alchemtech.playlistmaker.data.db.entity
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// TODO:
@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTracks(tracks: List<TrackEntity>)

    @Delete
    suspend fun delTrack(track: TrackEntity)

    @Query("SELECT * FROM tracks_table")
    suspend fun getAllTracks(): List<TrackEntity>

    @Query("SELECT * FROM tracks_table")
    suspend fun getIdFavoriteTracks(): List<String>
}