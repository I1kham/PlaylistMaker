package com.alchemtech.playlistmaker.data.network

import com.alchemtech.playlistmaker.data.dto.response.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}
