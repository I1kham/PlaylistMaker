package com.alchemtech.playlistmaker.data.db.play_lists_repo

import com.alchemtech.playlistmaker.data.converters.TracksStringConvertor
import com.alchemtech.playlistmaker.data.cover_repository.CoversRepository
import com.alchemtech.playlistmaker.data.db.entity.PlayListDao
import com.alchemtech.playlistmaker.data.db.entity.PlayListEntity
import com.alchemtech.playlistmaker.domain.db.PlayListsRepository
import com.alchemtech.playlistmaker.domain.db.TracksDbRepository
import com.alchemtech.playlistmaker.domain.entity.PlayList
import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PlayListsRepositoryImpl(
    private val tracksDbRepository: TracksDbRepository,
    private val playListDao: PlayListDao,
    private val tracksStringConvertor: TracksStringConvertor,
    private val coversRepository: CoversRepository,
) : PlayListsRepository {

    override suspend fun cleaning() {
        withContext(Dispatchers.IO) {
            val usedTracksIds = HashSet<String>()
            playListDao.getAllTracksIdFromAllPlayList().map {
                tracksStringConvertor.mapIDsStringToList(it).map { usedTracksIds.add(it) }
            }
            tracksDbRepository.getAllTrackList().collect { listTracks ->
                listTracks.map {
                    if (!usedTracksIds.contains(it.trackId) && !it.isFavorite) {
                        tracksDbRepository.deleteTrack(it.trackId)
                    }
                }

            }
        }
    }

    override suspend fun addPlayList(playList: PlayList) {
        withContext(Dispatchers.IO) {
            val name = playList.name
            val id = playListDao.getRowCount() + 1
            val description = playList.description
            val coverUri = coversRepository.saveCover(id, playList.coverUri).toString()
            val tracksId = tracksStringConvertor.mapListToIDs(playList.tracks)
            playListDao.addPlayList(
                PlayListEntity(
                    id,
                    name,
                    description,
                    coverUri,
                    tracksId
                )
            )
        }
    }

    override suspend fun removePlayList(id: Long) {
        withContext(Dispatchers.IO) {
            playListDao.removePlayList(id)
            coversRepository.deleteCover(id)
        }
    }


    override fun getAllPlayLists(): Flow<List<PlayList>> {
        return playListDao.getAllPlayLists().map { playListEntity: List<PlayListEntity> ->
            playListEntity.map { playList -> playList.convertPlaylistEntityToPlayList() }
        }
    }


    override suspend fun getTracks(id: Long): Flow<List<Track>> {
        return playListDao.getTracksFlowIdFromPlayList(id).map {
            tracksStringConvertor.mapIDsStringToList(it).map { tracksDbRepository.getTrackByID(it) }
        }
    }


    override suspend fun addToList(playListId: Long, trackId: String): Boolean {
        return withContext(Dispatchers.IO) {
            var isAdded: Boolean = false
            val tracksList =
                tracksStringConvertor
                    .mapIDsStringToList(
                        playListDao.getTracksIdFromPlayList(playListId)
                    ).toMutableList()
            if ((!tracksList.contains(trackId)).also { isAdded = it }) {
                tracksList.add(0, trackId)
                playListDao.updatePlaylistTracks(
                    playListId,
                    tracksStringConvertor.mapListIdToString(tracksList.toList())
                )
            }
            isAdded
        }
    }

    override suspend fun removeFromList(listId: Long, trackId: Long): Boolean {
        return withContext(Dispatchers.IO) {
            var removed = false
            val tracksList =
                tracksStringConvertor
                    .mapIDsStringToList(
                        playListDao.getTracksIdFromPlayList(listId)
                    ).toMutableList()
            if ((!tracksList.remove(trackId.toString())).also { removed = it }) {
                playListDao.updatePlaylistTracks(
                    listId,
                    tracksStringConvertor.mapListIdToString(tracksList)
                )
            }
            removed
        }
    }

    override suspend fun getPlayList(id: Long): PlayList {
        return withContext(Dispatchers.Default) {
            playListDao.getPlayList(id).convertPlaylistEntityToPlayList()


        }
    }

    override suspend fun updatePlaylistInfo(
        id: Long,
        playListName: String,
        playListDescription: String?,
        uri: String?,
    ) {
        withContext(Dispatchers.IO) {
            val coverUri = coversRepository.saveCover(id, uri)
            playListDao.updatePlaylistInfo(
                id,
                playListName,
                playListDescription,
                coverUri.toString()
            )
        }
    }

    private suspend fun PlayListEntity.convertPlaylistEntityToPlayList(): PlayList {
        return PlayList(
            this.playListId,
            this.name,
            this.description,
            this.coverUri,
            getTacksListByIDList(
                tracksStringConvertor.mapIDsStringToList(this.tracks)
            )
        )
    }

    private suspend fun getTacksListByIDList(idList: List<String>): List<Track> {
        return withContext(Dispatchers.IO) {
            val newTrackList = mutableListOf<Track>()
            for (id in idList) {
                newTrackList.add((tracksDbRepository.getTrackByID(id)))
            }
            newTrackList
        }
    }
}