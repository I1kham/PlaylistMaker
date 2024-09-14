package com.alchemtech.playlistmaker.domain.db

import com.alchemtech.playlistmaker.domain.entity.PlayList
import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow

interface PlayListsRepository {
    suspend fun addPlayList(playList: PlayList)
    suspend fun removePlayList(name: String)
    fun getAllPlayLists(): Flow<List<PlayList>>
    fun getTracks(name: String): Flow<List<Track>>
    suspend fun addToList(name: String, track: Track):Boolean
}