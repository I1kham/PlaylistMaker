package com.alchemtech.playlistmaker.domain.utils

import android.media.MediaPlayer
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding

class PlayerInteractorImpl:PlayerInteractor {
    override var playerState : Int
        get() = playerState
        set(value) {playerState=value}

    override fun pausePlayer(player: MediaPlayer, binding: ActivityPlayerBinding) {
        player.pause()
        playerState = STATE_PAUSED
        binding.playBut.setImageResource(R.drawable.play_but)
    }

    override fun startPlayer(player: MediaPlayer, binding: ActivityPlayerBinding) {
        player.start()
        playerState = STATE_PLAYING
        binding.playBut.setImageResource(R.drawable.pause_but)
    }

    override fun playbackControl(player: MediaPlayer, binding: ActivityPlayerBinding) {
        when (playerState) {
            STATE_PLAYING -> {
                pausePlayer(player, binding)
            }

            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer(player, binding)
            }
        }
    }
    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
    }
}