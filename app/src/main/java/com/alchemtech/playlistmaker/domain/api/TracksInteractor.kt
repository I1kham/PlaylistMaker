package com.alchemtech.playlistmaker.domain.api

import com.alchemtech.playlistmaker.domain.TracksResponseContainer

interface TracksInteractor {
    fun searchTracksInteractor(expression: String, consumer: TracksConsumer)

    interface TracksConsumer {
        fun consume(foundTracks: TracksResponseContainer)
    }
}