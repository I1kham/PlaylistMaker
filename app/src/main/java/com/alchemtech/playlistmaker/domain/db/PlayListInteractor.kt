package com.alchemtech.playlistmaker.domain.db

import android.net.Uri
import com.alchemtech.playlistmaker.domain.entity.PlayList
import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow

interface PlayListInteractor {
    suspend fun addPlayList(playList: PlayList)
    suspend fun removePlayList(id: Long)
    fun getAllPlayLists(): Flow<List<PlayList>>
    suspend fun getTracks(id: Long): Flow<List<Track>>
    suspend fun addToList(id: Long, track: Track): Boolean
    suspend fun getPlayList(id: Long): Flow<PlayList>
    suspend fun updatePlaylistInfo(
        id: Long,
        playListName: String,
        playListDescription: String?,
        coverUri: Uri?,
    )

    suspend fun cleaningDb()
}
