package com.alchemtech.playlistmaker.data.db.favorite_list_repo

import com.alchemtech.playlistmaker.data.converters.PlayListDbConvertor
import com.alchemtech.playlistmaker.data.db.entity.PlayListDao
import com.alchemtech.playlistmaker.data.db.entity.PlayListEntity
import com.alchemtech.playlistmaker.domain.db.PlayListsRepository
import com.alchemtech.playlistmaker.domain.entity.PlayList
import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class PlayListsRepositoryImpl(
    private val playListDao: PlayListDao,
    private val playListDbConvertor: PlayListDbConvertor,

    ) : PlayListsRepository {
    override suspend fun addPlayList(playList: PlayList) {
        playListDao.addPlayList(playListDbConvertor.map(playList))
    }

    override suspend fun removePlayList(name: String) {
        playListDao.removePlayList(name)
    }

    override fun getAllPlayLists(): Flow<List<PlayList>> {
        return playListDao.getAllPlayLists().map { playListEntity: List<PlayListEntity> ->
            playListEntity.map { playList -> playListDbConvertor.map(playList) }
        }
    }

    override  fun getTracks(name: String): Flow<List<Track>> = flow {
        playListDbConvertor.map(playListDao.getTracks(name))

    }

    override suspend fun updatePlaylist(name: String, trackList: List<Track>) {
        playListDao.updatePlaylist(
            name,
            playListDbConvertor.map(trackList)
        )
    }

}