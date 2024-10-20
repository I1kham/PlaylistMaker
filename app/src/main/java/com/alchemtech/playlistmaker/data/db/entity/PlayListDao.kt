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

    @Query("DELETE FROM play_lists_table WHERE playListId = :id")
    suspend fun removePlayList(id: Long)

    @Query("SELECT * FROM play_lists_table")
    fun getAllPlayLists(): Flow<List<PlayListEntity>>

    @Query("SELECT tracks FROM play_lists_table WHERE playListId = :id")
    suspend fun getTracksIdFromPlayList(id: Long): String?

    @Query("UPDATE play_lists_table SET tracks = :trackList WHERE playListId = :id ")
    suspend fun updatePlaylistTracks(id: Long, trackList: String)

    @Query("SELECT COUNT(playListId) FROM play_lists_table")
    suspend fun getRowCount(): Long

    @Query("SELECT * FROM play_lists_table WHERE playListId = :id ")
     fun getPlayList(id: Long): Flow<PlayListEntity>

    @Query("UPDATE play_lists_table SET name = :playListName,description = :playListDescription, coverUri =:uri WHERE playListId = :id ")
    suspend fun updatePlaylistInfo(id: Long, playListName: String, playListDescription: String?, uri: String?)

    // TODO: test
    @Query("SELECT tracks FROM play_lists_table")
    suspend fun getAllTracksIdFromAllPlayList(): List<String>

}