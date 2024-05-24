package com.alchemtech.playlistmaker.domain.impl

import com.alchemtech.playlistmaker.domain.api.TracksInteractor
import com.alchemtech.playlistmaker.domain.api.TracksRepository
import java.util.concurrent.Executors
/*
класс реализующий интерфейс
и метод searchTracksInteractor чтобы получить
список фильмов, используя сетевой клиент, и вернуть его*/
class TracksInteractorImpl( val repository: TracksRepository) : TracksInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchTracksInteractor(expression: String, consumer: TracksInteractor.TracksConsumer) {
        executor.execute {
            consumer.consume(repository.searchTracks(expression))
        }
    }
}