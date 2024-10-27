package com.alchemtech.playlistmaker.data.db.favorite_list_repo

import com.alchemtech.playlistmaker.data.converters.TrackDbConvertor
import com.alchemtech.playlistmaker.data.db.entity.TrackDao
import com.alchemtech.playlistmaker.data.db.entity.TrackEntity
import com.alchemtech.playlistmaker.domain.db.TracksDbRepository
import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TracksDbRepositoryImpl(
    private val tracksDao: TrackDao,
    private val trackDbConvertor: TrackDbConvertor,
) : TracksDbRepository {

    override suspend fun addToTracksDb(track: Track) {
        withContext(Dispatchers.IO) {
            tracksDao.addTrack(trackDbConvertor.map(track))
        }
    }

    override fun getFavoriteTrackList(): Flow<List<Track>> {
            return tracksDao.getFavoriteTracks().map { trackEntityList: List<TrackEntity> ->
                trackEntityList.map { trackEntity ->
                    trackDbConvertor.map(trackEntity)
                }
        }
    }

    override fun getAllTrackList(): Flow<List<Track>> {
        return tracksDao.getAllTracks().map { trackEntityList: List<TrackEntity> ->
            trackEntityList.map { trackEntity ->
                trackDbConvertor.map(trackEntity)
            }
        }
    }

    override suspend fun getTrackByID(id: String): Track {
        return trackDbConvertor.map(tracksDao.getTrackByID(id))
    }

    override suspend fun deleteTrack(trackId: String) {
        withContext(Dispatchers.IO) {
            tracksDao.deleteTrack(trackId)
        }
    }

    override suspend fun unLikeTrack(trackId: String) {
        withContext(Dispatchers.IO) {
            tracksDao.unLikeTrack(trackId)
        }
    }

    override suspend fun likeTrack(trackId: String) {
        withContext(Dispatchers.IO) {
            tracksDao.likeTrack(trackId)
        }
    }
}