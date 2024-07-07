package com.alchemtech.playlistmaker.domain.player

import com.alchemtech.playlistmaker.domain.api.PlayerRepository

class PlayerInteractorImlp(
    private val playerRepository: PlayerRepository,
) :    PlayerInteractor {
    private var onPreparedListenerConsumer: PlayerRepository.OnPreparedListenerConsumer? = null
    private var onCompletionListenerConsumer: PlayerRepository.OnCompletionListenerConsumer? = null
    private var pauseConsumer: PlayerInteractor.PauseConsumer? = null
    private var startConsumer: PlayerInteractor.StartConsumer? = null

    override fun setConsumers(
        onPreparedListenerConsumer: PlayerRepository.OnPreparedListenerConsumer,
        onCompletionListenerConsumer: PlayerRepository.OnCompletionListenerConsumer,
        pauseConsumer: PlayerInteractor.PauseConsumer,
        startConsumer: PlayerInteractor.StartConsumer,
    ) {
        this.onPreparedListenerConsumer = onPreparedListenerConsumer
        this.onCompletionListenerConsumer = onCompletionListenerConsumer
        this.pauseConsumer = pauseConsumer
        this.startConsumer = startConsumer
    }

    override fun currentPosition(): Int {
        return playerRepository.currentPosition()
    }

    override fun duration(): Int {
        return playerRepository.duration()
    }

    override fun playerIsPlaying(): Boolean {
        return playerRepository.playerIsPlaying()
    }

    override fun pausePlayer() {
        playerRepository.pause()
        pauseConsumer!!.consume()
    }

    override fun startPlayer() {
        playerRepository.start()
        startConsumer!!.consume()
    }

    override fun release() {
        playerRepository.release()
    }

    override fun playbackControl() {
        when (playerRepository.playerIsPlaying()) {
            true -> {
                pausePlayer()
            }

            false -> {
                startPlayer()
            }
        }
    }

    override fun preparePlayer(source: String) {
        playerRepository.preparePlayer(
            onPreparedListenerConsumer!!,
            onCompletionListenerConsumer!!,
            source
        )
    }
}