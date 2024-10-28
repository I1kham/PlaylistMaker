package com.alchemtech.playlistmaker.domain.impl

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

    override suspend fun getTracks(listId: Long): Flow<List<Track>> {
        return playListsRepository.getTracks(listId)
    }

    override suspend fun addToList(listId: Long, trackId: String): Boolean {
        return playListsRepository.addToList(listId, trackId)
    }

    override suspend fun removeFromList(listId: Long, trackId: Long): Boolean {
        return playListsRepository.removeFromList(listId, trackId)
    }

    override suspend fun getPlayList(listId: Long): PlayList {
        return playListsRepository.getPlayList(listId)
    }

    override suspend fun updatePlaylistInfo(
        listId: Long,
        playListName: String,
        playListDescription: String?,
        coverUri: String?,
    ) {
        playListsRepository.updatePlaylistInfo(
            listId,
            playListName,
            playListDescription,
            coverUri
        )
    }

    override suspend fun cleaningDb() {
        playListsRepository.cleaning()
    }
}