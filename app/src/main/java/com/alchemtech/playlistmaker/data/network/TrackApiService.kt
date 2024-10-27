package com.alchemtech.playlistmaker.data.network

import com.alchemtech.playlistmaker.data.dto.response.TracksSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackApiService {
    @GET("/search?entity=song")
    suspend fun searchTracks(@Query("term") text: String): TracksSearchResponse
}