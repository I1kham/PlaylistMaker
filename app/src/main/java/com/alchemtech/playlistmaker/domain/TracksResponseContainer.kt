package com.alchemtech.playlistmaker.domain

import com.alchemtech.playlistmaker.domain.models.Track

data class TracksResponseContainer(
    val tracksList: List<Track>,
    val responseResultCode : Int
)
