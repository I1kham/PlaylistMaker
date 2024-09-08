package com.alchemtech.playlistmaker.domain.db

import com.alchemtech.playlistmaker.domain.entity.PlayList
import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow

interface PlayListInteractor {
    suspend fun addPlayList(playList: PlayList)
    suspend fun removePlayList(name: String)
    fun getAllPlayLists(): Flow<List<PlayList>>
    fun getTracks(name: String): Flow<List<Track>>
    suspend fun updatePlaylist(name: String, trackList: List<Track>)
}