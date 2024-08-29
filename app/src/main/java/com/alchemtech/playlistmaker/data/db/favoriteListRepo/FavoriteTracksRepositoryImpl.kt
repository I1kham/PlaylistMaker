package com.alchemtech.playlistmaker.data.db.favoriteListRepo

import com.alchemtech.playlistmaker.data.converters.TrackDbConvertor
import com.alchemtech.playlistmaker.data.db.entity.TrackDao
import com.alchemtech.playlistmaker.domain.db.FavoriteTracksRepository
import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteTracksRepositoryImpl(private val favoriteTRacksDao: TrackDao,
                                   private val trackDbConvertor:  TrackDbConvertor) :
    FavoriteTracksRepository {
    override suspend fun addToFavoriteList(track: Track) {
        favoriteTRacksDao.addTrack(trackDbConvertor.map(track))
    }

    override suspend fun removeFromFavoriteList(track: Track) {
        favoriteTRacksDao.removeTrack(trackDbConvertor.map(track))
    }

    override fun getFavoriteTrackList(): Flow<List<Track>> {
        return favoriteTRacksDao.getAllTracks().map {
            it.map { trackEntity ->
                trackDbConvertor.map(trackEntity)
            }
        }
    }
}