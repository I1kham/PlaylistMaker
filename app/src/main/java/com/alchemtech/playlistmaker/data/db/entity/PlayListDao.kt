package com.alchemtech.playlistmaker.data.db.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface PlayListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlayList(playListEntity: PlayListEntity)

    @Query("DELETE FROM play_lists_table WHERE name = :name")
    suspend fun removePlayList(name: String)

    @Query("SELECT * FROM play_lists_table")
    fun getAllPlayLists(): Flow<List<PlayListEntity>>

    @Query("SELECT tracks FROM play_lists_table WHERE name = :name")
    suspend fun getTracksIdFromPlayList(name: String): String?

    @Query("UPDATE play_lists_table SET tracks = :trackList WHERE name = :name ")
    suspend fun updatePlaylist(name: String, trackList: String)
}