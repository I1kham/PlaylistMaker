package com.alchemtech.playlistmaker.data.repository

import android.media.MediaPlayer
import com.alchemtech.playlistmaker.domain.api.PlayerRepository

class PlayerRepositoryImpl(private var mediaPlayer: MediaPlayer) : PlayerRepository {
    private var isPrepared = false
    override fun currentPosition(): Int {
        return if (isPrepared) {
            mediaPlayer.currentPosition
        } else {
            0
        }
    }

    override fun duration(): Int {
        return if (isPrepared) {
            mediaPlayer.duration
        } else {
            0
        }
    }

    override fun release() {
        isPrepared = false
        mediaPlayer.release()
    }

    override fun playerIsPlaying(): Boolean {
        return if (isPrepared) {
            mediaPlayer.isPlaying
        } else {
            true
        }
    }

    override fun pause() {
        if (isPrepared && mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    override fun start() {
        if (isPrepared) {
            mediaPlayer.start()
        }
    }

    override fun preparePlayer(
        onPreparedListenerConsumer: PlayerRepository.OnPreparedListenerConsumer,
        onCompletionListenerConsumer: PlayerRepository.OnCompletionListenerConsumer,
        source: String,
    ) {
        if (!isPrepared) {
            mediaPlayer.setDataSource(source)
            mediaPlayer.setOnPreparedListener {
                onPreparedListenerConsumer.consume()
                isPrepared = true
            }
            mediaPlayer.setOnCompletionListener {
                onCompletionListenerConsumer.consume()
            }
            mediaPlayer.prepareAsync()
        }
    }
}