package com.alchemtech.playlistmaker.domain.db

import com.alchemtech.playlistmaker.domain.entity.PlayList
import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow

interface PlayListInteractor {
    suspend fun addPlayList(playList: PlayList)
    suspend fun removePlayList(id: Long)
    fun getAllPlayLists(): Flow<List<PlayList>>
    fun getTracks(id: Long): Flow<List<Track>>
    suspend fun addToList(id: Long,track: Track):Boolean
}
