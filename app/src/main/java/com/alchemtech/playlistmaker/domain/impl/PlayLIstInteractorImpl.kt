package com.alchemtech.playlistmaker.domain.impl

import com.alchemtech.playlistmaker.data.cover_repository.CoversRepository
import com.alchemtech.playlistmaker.domain.db.PlayListInteractor
import com.alchemtech.playlistmaker.domain.db.PlayListsRepository
import com.alchemtech.playlistmaker.domain.entity.PlayList
import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow

class PlayLIstInteractorImpl(
    private val playListsRepository: PlayListsRepository,
    private val coversRepository: CoversRepository,
) : PlayListInteractor {
    override suspend fun addPlayList(playList: PlayList) {
        playList.coverUri?.let {
            playList.coverUri = coversRepository.saveCover(it)
        }
        playListsRepository.addPlayList(playList)
    }

    override suspend fun removePlayList(name: String) {
        playListsRepository.removePlayList(name)
    }

    override fun getAllPlayLists(): Flow<List<PlayList>> {
        return playListsRepository.getAllPlayLists()
    }

    override fun getTracks(name: String): Flow<List<Track>> {
        return playListsRepository.getTracks(name)
    }

    override suspend fun updatePlaylist(name: String, trackList: List<Track>) {
        playListsRepository.updatePlaylist(name, trackList)
    }

}