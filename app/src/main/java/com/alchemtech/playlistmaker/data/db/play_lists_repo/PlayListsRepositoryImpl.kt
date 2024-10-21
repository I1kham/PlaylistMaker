package com.alchemtech.playlistmaker.data.db.play_lists_repo

import androidx.core.net.toUri
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
import kotlinx.coroutines.flow.flow
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
                        println("deleting ${it.trackId}")
                        tracksDbRepository.deleteTrack(it.trackId)
                    }
                }

            }

        }
    }

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
        coversRepository.deleteCover(id)
    }


    override fun getAllPlayLists(): Flow<List<PlayList>> {
        return playListDao.getAllPlayLists().map { playListEntity: List<PlayListEntity> ->
            playListEntity.map { playList -> playList.convertPlaylistEntityToPlayList() }
        }
    }


    override suspend fun getTracks(id: Long): Flow<List<Track>> {
        val tracksId = playListDao.getTracksIdFromPlayList(id)
        val tracks = tracksStringConvertor.mapIDsStringToList(tracksId)
        return flow { emit(tracks.map { tracksDbRepository.getTrackByID(it) }) }
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
            tracksDbRepository.addToTracksDb(track)
            playListDao.updatePlaylistTracks(
                id,
                tracksStringConvertor.mapListIdToString(mutList)
            )
            isAdded = true
        }
        return isAdded
    }

    override suspend fun getPlayList(id: Long): Flow<PlayList> {
        return playListDao.getPlayList(id).map { it.convertPlaylistEntityToPlayList() }
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
            newTrackList.add((tracksDbRepository.getTrackByID(id)))
        }
        return newTrackList
    }
}