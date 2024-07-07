package com.alchemtech.playlistmaker.domain.impl

import com.alchemtech.playlistmaker.domain.api.TracksInteractor
import com.alchemtech.playlistmaker.domain.api.TracksRepository
import com.alchemtech.playlistmaker.util.Resource
import java.util.concurrent.Executors

class TracksInteractorImpl(private val tracksRepository: TracksRepository) : TracksInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchTracksInteractor(
        expression: String,
        consumer: TracksInteractor.TracksConsumer,
    ) {
        executor.execute {
            when (val resource = tracksRepository.searchTracks(expression)) {
                is Resource.Success -> {
                    consumer.consume(resource.data, null)
                }

                is Resource.Error -> {
                    consumer.consume(null, resource.message)
                }
            }
        }
    }
}