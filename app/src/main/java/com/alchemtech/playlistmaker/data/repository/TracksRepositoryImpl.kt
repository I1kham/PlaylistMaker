package com.alchemtech.playlistmaker.data.repository

import com.alchemtech.playlistmaker.data.dto.request.TracksSearchRequest
import com.alchemtech.playlistmaker.data.dto.response.TracksSearchResponse
import com.alchemtech.playlistmaker.data.network.NetworkClient
import com.alchemtech.playlistmaker.domain.api.TracksRepository
import com.alchemtech.playlistmaker.domain.entity.Track

// TODO: здесь можно было бы реализовать консумер, сдоступом к переменной resulCode и применить логику реагирования на ошибки 
class TracksRepositoryImpl(private val networkClient: NetworkClient) : TracksRepository {
    override fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        if (response.resultCode == 200) {
            return (response as TracksSearchResponse).results.map {
                Track(
                    it.trackName,
                    it.artistName,
                    it.trackTimeMillis,
                    it.artworkUrl100,
                    it.trackId,
                    it.collectionName,
                    it.releaseDate,
                    it.primaryGenreName,
                    it.country,
                    it.previewUrl,
                )
            }
        } else {
            return emptyList()
        }
    }
}