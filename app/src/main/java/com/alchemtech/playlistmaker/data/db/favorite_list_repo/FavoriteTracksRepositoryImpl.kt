package com.alchemtech.playlistmaker.data.db.favorite_list_repo

import com.alchemtech.playlistmaker.data.converters.TrackDbConvertor
import com.alchemtech.playlistmaker.data.db.entity.TrackDao
import com.alchemtech.playlistmaker.data.db.entity.TrackEntity
import com.alchemtech.playlistmaker.domain.db.FavoriteTracksRepository
import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteTracksRepositoryImpl(
    private val TracksDao: TrackDao,
    private val trackDbConvertor: TrackDbConvertor,
) : FavoriteTracksRepository {
    override suspend fun addToFavoriteList(track: Track) {
        TracksDao.addTrack(trackDbConvertor.map(track))
    }

    override suspend fun removeFromFavoriteList(track: Track) {
        TracksDao.removeTrack(trackDbConvertor.map(track))
    }

    override fun getFavoriteTrackList(): Flow<List<Track>> {
        return TracksDao.getAllTracks().map { trackEntityList: List<TrackEntity> ->
            trackEntityList.map { trackEntity ->
                trackDbConvertor.map(trackEntity)
            }
        }
    }
}