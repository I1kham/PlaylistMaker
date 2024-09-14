package com.alchemtech.playlistmaker.data.db.favorite_list_repo

import com.alchemtech.playlistmaker.data.converters.TrackDbConvertor
import com.alchemtech.playlistmaker.data.db.entity.TrackDao
import com.alchemtech.playlistmaker.data.db.entity.TrackEntity
import com.alchemtech.playlistmaker.domain.db.FavoriteTracksRepository
import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteTracksRepositoryImpl(
    private val tracksDao: TrackDao,
    private val trackDbConvertor: TrackDbConvertor,
) : FavoriteTracksRepository {
    override suspend fun addToFavoriteList(track: Track) {
        tracksDao.addTrack(trackDbConvertor.map(track))
    }

    override suspend fun removeFromFavoriteList(track: Track) {
        tracksDao.removeTrack(trackDbConvertor.map(track))
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
}