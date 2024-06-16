package com.alchemtech.playlistmaker.domain.api

import com.alchemtech.playlistmaker.domain.entity.Track

interface TracksInteractor {
    fun searchTracksInteractor(expression: String, consumer: TracksConsumer)

    interface TracksConsumer {
        fun consume(foundedTracks: List<Track>?, errorMessage: String?)
    }
}