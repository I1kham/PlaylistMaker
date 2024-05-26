package com.alchemtech.playlistmaker.domain.api

import com.alchemtech.playlistmaker.domain.entity.Track

/*объект заготовка для класса для
слой Presentation будет общаться со слоем Domain. todo use case*/
interface TracksInteractor {
    fun searchTracksInteractor(expression: String, consumer: TracksConsumer)

    interface TracksConsumer {
        fun consume(foundedTracks: List<Track>)
    }
}