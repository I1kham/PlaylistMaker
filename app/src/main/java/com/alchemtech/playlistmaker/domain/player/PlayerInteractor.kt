package com.alchemtech.playlistmaker.domain.player

import com.alchemtech.playlistmaker.domain.api.PlayerRepository

interface PlayerInteractor {
    fun pausePlayer()

    fun startPlayer()
    fun playbackControl()
    fun release()

    fun preparePlayer()

    fun setConsumers(
        onPreparedListenerConsumer: PlayerRepository.OnPreparedListenerConsumer,
        onCompletionListenerConsumer: PlayerRepository.OnCompletionListenerConsumer,
        pauseConsumer: PauseConsumer,
        startConsumer: StartConsumer,

        )

    fun currentPosition(): String
    fun duration(): String
    fun playerIsPlaying(): Boolean
    interface PauseConsumer {
        fun consume()
    }

    interface StartConsumer {
        fun consume()
    }

}