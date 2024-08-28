package com.alchemtech.playlistmaker.data.repository

import com.alchemtech.playlistmaker.data.converters.TrackDtoConvertor
import com.alchemtech.playlistmaker.data.db.entity.TrackDao
import com.alchemtech.playlistmaker.data.dto.request.TracksSearchRequest
import com.alchemtech.playlistmaker.data.dto.response.TracksSearchResponse
import com.alchemtech.playlistmaker.data.dto.trackDto.TrackDto
import com.alchemtech.playlistmaker.data.network.NetworkClient
import com.alchemtech.playlistmaker.domain.api.TracksRepository
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    private val trackDao: TrackDao,
    private val trackDtoConvertor: TrackDtoConvertor
) : TracksRepository {
    override fun searchTracks(expression: String): Flow<Resource<List<Track>>> = flow {
        val response = networkClient.doRequest(TracksSearchRequest(expression))

        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error(response.resultCode))
            }

            200 -> {
                emit(Resource.Success((response as TracksSearchResponse).results.map { trackDto ->
                    trackDtoConvertor.map(checkForFavorite(trackDto))
                }))
            }

            else -> {
                emit(Resource.Error(response.resultCode))
            }
        }
    }

    private suspend fun checkForFavorite(trackDto: TrackDto): TrackDto {
        val favoriteIdList = trackDao.getIdFavoriteTracks()
        if (favoriteIdList.contains(trackDto.trackId)) {
            trackDto.isFavorite = true
        }
        return trackDto
    }
}