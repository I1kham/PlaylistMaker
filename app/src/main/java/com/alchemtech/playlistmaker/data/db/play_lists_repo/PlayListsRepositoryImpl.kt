package com.alchemtech.playlistmaker.data.db.play_lists_repo

import com.alchemtech.playlistmaker.data.converters.PlayListDbConvertor
import com.alchemtech.playlistmaker.data.converters.TracksStringConvertor
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
    private val tracksStringConvertor: TracksStringConvertor,

    ) : PlayListsRepository {
    override suspend fun addPlayList(playList: PlayList) {
        println("88888"+playList) // TODO:
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

    override fun getTracks(name: String): Flow<List<Track>> = flow {
        tracksStringConvertor.map(playListDao.getTracks(name))

    }

    override suspend fun updatePlaylist(name: String, trackList: List<Track>) {
        playListDao.updatePlaylist(
            name,
            tracksStringConvertor.map(trackList)
        )
    }

}