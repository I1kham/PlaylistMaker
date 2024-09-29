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
        val name = playList.name
        val id = playListDao.getRowCount() + 1
        val description = playList.description
        val coverUri = coversRepository.saveCover(id, playList.coverUri).toString()
        val tracksId = tracksStringConvertor.mapListToIDs(playList.tracks)
        return playListDao.addPlayList(
            PlayListEntity(
                id,
                name,
                description,
                coverUri,
                tracksId
            )
        )
    }

    override suspend fun removePlayList(id: Long) {
        playListDao.removePlayList(id)
    }


    override fun getAllPlayLists(): Flow<List<PlayList>> {
        return playListDao.getAllPlayLists().map { playListEntity: List<PlayListEntity> ->
            playListEntity.map { playList -> playList.convertPlaylistEntityToPlayList() }
        }
    }


    override suspend fun getTracks(id: Long): Flow<List<Track>> {
        val tracksId = playListDao.getTracksIdFromPlayList(id)
        val tracks = tracksStringConvertor.mapIDsStringToList(tracksId)
        return flow { emit(tracks.map { favoriteTracksRepository.getTrackByID(it) }) }
    }


    override suspend fun addToList(id: Long, track: Track): Boolean {
        var isAdded = false
        val mutList = mutableListOf<String>()
        val a = playListDao.getTracksIdFromPlayList(id)
        mutList.addAll(
            tracksStringConvertor.mapIDsStringToList(a)
        )
        if (!mutList.contains(track.trackId)) {
            mutList.add(track.trackId)
            favoriteTracksRepository.addToFavoriteList(track)
            playListDao.updatePlaylistTracks(
                id,
                tracksStringConvertor.mapListIdToString(mutList)
            )
            isAdded = true
        }
        return isAdded
    }

    override suspend fun getPlayList(id: Long): PlayList {
        return playListDao.getPlayList(id).convertPlaylistEntityToPlayList()
    }

    override suspend fun updatePlaylistInfo(
        id: Long,
        playListName: String,
        playListDescription: String?,
        uri: String?,
    ) {

        val coverUri = coversRepository.saveCover(id, uri?.toUri())
        playListDao.updatePlaylistInfo(id, playListName, playListDescription, coverUri.toString())
    }

    private suspend fun PlayListEntity.convertPlaylistEntityToPlayList(): PlayList {
        return PlayList(
            this.playListId,
            this.name,
            this.description,
            this.coverUri?.toUri(),
            getTacksListByIDList(
                tracksStringConvertor.mapIDsStringToList(this.tracks)
            )
        )
    }

    private suspend fun getTacksListByIDList(idList: List<String>): List<Track> {
        val newTrackList = mutableListOf<Track>()
        for (id in idList) {
            newTrackList.add((favoriteTracksRepository.getTrackByID(id)))
        }
        return newTrackList
    }
}