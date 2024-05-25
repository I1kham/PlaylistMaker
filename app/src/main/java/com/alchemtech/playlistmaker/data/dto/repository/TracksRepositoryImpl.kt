package com.alchemtech.playlistmaker.data.dto.repository

import com.alchemtech.playlistmaker.data.dto.request.TracksSearchRequest
import com.alchemtech.playlistmaker.data.dto.response.TracksSearchResponse
import com.alchemtech.playlistmaker.data.network.NetworkClient
import com.alchemtech.playlistmaker.domain.api.TracksRepository
import com.alchemtech.playlistmaker.domain.models.Track

/*todo
*  объект отправляющий запрос в сеть и
* получающий из сети ответ возвращяющий контейнер
* с списком треков и кода ответа от сервера*/
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
                    //it.getArtworkUrl512(it.artworkUrl100)
                )
            }
        } else {
            return emptyList()
        }

    }
}