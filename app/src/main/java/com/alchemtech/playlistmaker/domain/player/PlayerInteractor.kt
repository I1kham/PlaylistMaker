package com.alchemtech.playlistmaker.domain.player

import com.alchemtech.playlistmaker.domain.api.PlayerRepository

interface PlayerInteractor {
    fun pausePlayer()

    fun startPlayer()
    fun playbackControl()
    fun release()

    fun preparePlayer(source: String)

    fun setConsumers(
        onPreparedListenerConsumer: PlayerRepository.OnPreparedListenerConsumer,
        onCompletionListenerConsumer: PlayerRepository.OnCompletionListenerConsumer,
        pauseConsumer: PauseConsumer,
        startConsumer: StartConsumer,

        )

    fun currentPosition(): Int
    fun duration(): Int
    fun playerIsPlaying(): Boolean
    interface PauseConsumer {
        fun consume()
    }

    interface StartConsumer {
        fun consume()
    }

}