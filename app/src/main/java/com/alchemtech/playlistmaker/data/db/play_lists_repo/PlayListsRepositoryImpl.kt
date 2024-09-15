package com.alchemtech.playlistmaker.data.db.play_lists_repo

import androidx.core.net.toUri
import com.alchemtech.playlistmaker.data.converters.TracksStringConvertor
import com.alchemtech.playlistmaker.data.cover_repository.CoversRepository
import com.alchemtech.playlistmaker.data.db.entity.PlayListDao
import com.alchemtech.playlistmaker.data.db.entity.PlayListEntity
import com.alchemtech.playlistmaker.domain.db.FavoriteTracksRepository
import com.alchemtech.playlistmaker.domain.db.PlayListsRepository
import com.alchemtech.playlistmaker.domain.entity.PlayList
import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class PlayListsRepositoryImpl(
    private val favoriteTracksRepository: FavoriteTracksRepository,
    private val playListDao: PlayListDao,
    private val tracksStringConvertor: TracksStringConvertor,
    private val coversRepository: CoversRepository,
    ) : PlayListsRepository {
    override suspend fun addPlayList(playList: PlayList) {
        val playListEntity = playList.convertPlaylistIDs()
        playListEntity.playListId = playListDao.getRowCount()+1
        playList.coverUri?.let {
            playList.coverUri = coversRepository.saveCover(playList.id, it)
        }
        playListDao.addPlayList(playListEntity)
    }

    override suspend fun removePlayList(id: Long) {
        playListDao.removePlayList(id)
    }


    override fun getAllPlayLists(): Flow<List<PlayList>> {
        return playListDao.getAllPlayLists().map { playListEntity: List<PlayListEntity> ->
            playListEntity.map { playList -> playList.convertPlaylistTracks() }
        }
    }


    override fun getTracks(id: Long): Flow<List<Track>> = flow {
        tracksStringConvertor.map(playListDao.getTracksIdFromPlayList(id))

    }


    override suspend fun addToList(id: Long, track: Track): Boolean {
        var isAdded =false
        val mutList = mutableListOf<String>()
        mutList.addAll(
            tracksStringConvertor.mapIDsListToList(playListDao.getTracksIdFromPlayList(id))
        )
        if (!mutList.contains(track.trackId)) {
            mutList.add(track.trackId)
            favoriteTracksRepository.addToFavoriteList(track)
            playListDao.updatePlaylist(
                id,
                tracksStringConvertor.mapListIdToString(mutList)
            )
            isAdded = true
        }

        return isAdded
    }

    private fun PlayList.convertPlaylistIDs(): PlayListEntity {
        return PlayListEntity(
            this.id,
            this.name,
            this.description,
            this.coverUri.toString(),
            tracksStringConvertor.mapListToIDs(this.tracks)
        )
    }

    private suspend fun PlayListEntity.convertPlaylistTracks(): PlayList {
        return PlayList(
            this.playListId,
            this.name,
            this.description,
            this.coverUri?.toUri(),
            getTackListByIList(
                tracksStringConvertor.mapIDsListToList(this.tracks)
            )
        )
    }

    private suspend fun getTrackByID(id: String): Track {
        return favoriteTracksRepository.getTrackByID(id)
    }

    private suspend fun getTackListByIList(idList: List<String>): List<Track> {
        val newTrackList = mutableListOf<Track>()
        for (id in idList) {
            newTrackList.add((getTrackByID(id)))
        }
        return newTrackList
    }
}