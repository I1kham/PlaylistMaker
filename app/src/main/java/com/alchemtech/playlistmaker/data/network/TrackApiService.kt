package com.alchemtech.playlistmaker.data.network

import com.alchemtech.playlistmaker.data.dto.response.TracksSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
/*todo объект заготовка возвращает объект TracksSearchResponse */
interface TrackApiService {
    @GET("/search?entity=song")
    fun searchTracks(@Query("term") text: String): Call<TracksSearchResponse>
}