package com.alchemtech.playlistmaker.data.repository

import android.media.MediaPlayer
import com.alchemtech.playlistmaker.domain.api.PlayerRepository

class PlayerRepositoryImpl(
    private val source: String,
) : PlayerRepository {
    private var mediaPlayer = MediaPlayer()

    override fun currentPosition(): Int {
        return mediaPlayer.currentPosition
    }

    override fun duration(): Int {
        return mediaPlayer.duration
    }

    override fun release() {
        mediaPlayer.release()
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
    ) {
println("-----------------------------------------------------------------")
        mediaPlayer.setDataSource(source)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            onPreparedListenerConsumer.consume()
        }
        mediaPlayer.setOnCompletionListener {
            onCompletionListenerConsumer.consume()
        }
    }
}