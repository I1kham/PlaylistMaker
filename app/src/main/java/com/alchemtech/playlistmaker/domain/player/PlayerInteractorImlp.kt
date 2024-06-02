package com.alchemtech.playlistmaker.domain.player

import com.alchemtech.playlistmaker.domain.api.PlayerRepository

class PlayerInteractorImlp(
    private val player: PlayerRepository,
) :
    PlayerInteractor {
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
        return player.currentPosition()
    }

    override fun duration(): Int {
        return player.duration()
    }

    override fun playerIsPlaying(): Boolean {
        return player.playerIsPlaying()
    }

    override fun pausePlayer() {
        player.pause()
        pauseConsumer!!.consume()
    }

    override fun startPlayer() {
        player.start()
        startConsumer!!.consume()
    }

    override fun release() {
        player.release()
    }

    override fun playbackControl() {
        when (player.playerIsPlaying()) {
            true -> {
                pausePlayer()
            }

            false -> {
                startPlayer()
            }
        }
    }

    override fun preparePlayer() {
        player.preparePlayer(
            onPreparedListenerConsumer!!,
            onCompletionListenerConsumer!!
        )
    }
}