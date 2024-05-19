package com.alchemtech.playlistmaker.data.dto

import com.alchemtech.playlistmaker.data.network.NetworkClient
import com.alchemtech.playlistmaker.domain.TracksResponseContainer
import com.alchemtech.playlistmaker.domain.api.TracksRepository
import com.alchemtech.playlistmaker.domain.models.Track

class TracksRepositoryImpl(private val networkClient: NetworkClient) : TracksRepository {
    override fun searchTracks(expression: String): TracksResponseContainer {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        if (response.resultCode == 200) {
            return TracksResponseContainer((response as TracksSearchResponse).results.map {
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
                    //   it.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")
                )
            }, responseResultCode = response.resultCode)
        } else {
            return TracksResponseContainer(
                emptyList(),
                responseResultCode = response.resultCode
            )
        }

    }
}