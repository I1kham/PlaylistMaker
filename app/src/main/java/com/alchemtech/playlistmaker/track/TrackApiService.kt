package com.alchemtech.playlistmaker.track

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackApiService {
    @GET("/search?entity=song")
    fun search(@Query("term") text: String): Call<TracksResponse>
}