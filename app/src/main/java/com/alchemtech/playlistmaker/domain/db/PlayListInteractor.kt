package com.alchemtech.playlistmaker.domain.db

import com.alchemtech.playlistmaker.domain.entity.PlayList
import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow

interface PlayListInteractor {
    suspend fun addPlayList(playList: PlayList)
    suspend fun removePlayList(id: Long)
    fun getAllPlayLists(): Flow<List<PlayList>>
    suspend fun getTracks(listId: Long): Flow<List<Track>>
    suspend fun addToList(listId: Long, trackId: String): Boolean
    suspend fun removeFromList(listId:Long,trackId: Long):Boolean
    suspend fun getPlayList(listId: Long): PlayList
    suspend fun updatePlaylistInfo(
        listId: Long,
        playListName: String,
        playListDescription: String?,
        coverUri: String?,
    )
    suspend fun cleaningDb()
}
