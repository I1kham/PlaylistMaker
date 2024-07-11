package com.alchemtech.playlistmaker.data.repository

import android.media.MediaPlayer
import com.alchemtech.playlistmaker.domain.api.PlayerRepository

class PlayerRepositoryImpl(private var mediaPlayer: MediaPlayer) : PlayerRepository {
    private var prepared = false
    override fun currentPosition(): Int {
        return mediaPlayer.currentPosition
    }

    override fun isPrepared(): Boolean {
        return prepared
    }

    override fun duration(): Int {
        return mediaPlayer.duration
    }

    override fun release() {
        mediaPlayer.release()
        prepared = false
    }

    override fun playerIsPlaying(): Boolean {
        return mediaPlayer.isPlaying
    }

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun start() {
        mediaPlayer.start()
    }

    override fun preparePlayer(
        onPreparedListenerConsumer: PlayerRepository.OnPreparedListenerConsumer,
        onCompletionListenerConsumer: PlayerRepository.OnCompletionListenerConsumer,
        source: String,
    ) {
        mediaPlayer.setDataSource(source)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            onPreparedListenerConsumer.consume()
//            PlayerRepository.OnPreparedListenerConsumer { isPrepared = true }.consume()
        }
        mediaPlayer.setOnCompletionListener {
            onCompletionListenerConsumer.consume()
        }
    }
}