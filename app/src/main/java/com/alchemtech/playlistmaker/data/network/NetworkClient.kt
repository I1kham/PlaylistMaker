package com.alchemtech.playlistmaker.data.network

import com.alchemtech.playlistmaker.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}