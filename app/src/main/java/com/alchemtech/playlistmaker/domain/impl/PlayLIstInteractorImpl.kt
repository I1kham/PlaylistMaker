package com.alchemtech.playlistmaker.domain.impl

import android.net.Uri
import com.alchemtech.playlistmaker.domain.db.PlayListInteractor
import com.alchemtech.playlistmaker.domain.db.PlayListsRepository
import com.alchemtech.playlistmaker.domain.entity.PlayList
import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow

class PlayLIstInteractorImpl(
    private val playListsRepository: PlayListsRepository,
) : PlayListInteractor {
    override suspend fun addPlayList(playList: PlayList) {
        playListsRepository.addPlayList(playList)
    }

    override suspend fun removePlayList(id: Long) {
        playListsRepository.removePlayList(id)
    }

    override fun getAllPlayLists(): Flow<List<PlayList>> {
        return playListsRepository.getAllPlayLists()
    }

    override suspend fun getTracks(id: Long): Flow<List<Track>> {
        return playListsRepository.getTracks(id)
    }

    override suspend fun addToList(id: Long, track: Track): Boolean {
        return playListsRepository.addToList(id, track)
    }

    override suspend fun getPlayList(id: Long): PlayList {
        return playListsRepository.getPlayList(id)
    }

    override suspend fun updatePlaylistInfo(
        id: Long,
        playListName: String,
        playListDescription: String?,
        coverUri: Uri?
    ) {
        playListsRepository.updatePlaylistInfo(id, playListName, playListDescription, coverUri.toString())
    }

    override suspend fun cleaningDb() {
        playListsRepository.cleaning()
    }
}