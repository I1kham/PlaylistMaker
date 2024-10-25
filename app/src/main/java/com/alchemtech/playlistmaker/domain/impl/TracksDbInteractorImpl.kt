package com.alchemtech.playlistmaker.domain.impl

import com.alchemtech.playlistmaker.domain.db.TracksDbInteractor
import com.alchemtech.playlistmaker.domain.db.TracksDbRepository
import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.flow.Flow

class TracksDbInteractorImpl(
    private val tracksDbRepository: TracksDbRepository,
) : TracksDbInteractor {
    override suspend fun addToTrackDb(track: Track) {
        println(track)
        tracksDbRepository.addToTracksDb(track)
    }

    override fun getFavoriteTrackList(): Flow<List<Track>> {
        return tracksDbRepository.getFavoriteTrackList()
    }

    override fun getAllTrackList(): Flow<List<Track>> {
        return tracksDbRepository.getAllTrackList()
    }

    override suspend fun deleteTrack(trackId: String) {
        tracksDbRepository.deleteTrack(trackId)
    }

    override suspend fun unLikeTrack(trackId: String) {
        tracksDbRepository.unLikeTrack(trackId)
    }

    override suspend fun getTrackById(id: String): Track {
        return tracksDbRepository.getTrackByID(id)
    }
}