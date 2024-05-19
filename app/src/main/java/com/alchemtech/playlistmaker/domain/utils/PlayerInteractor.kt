package com.alchemtech.playlistmaker.domain.utils

import android.media.MediaPlayer

import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding

interface PlayerInteractor {
    var playerState : Int

    fun pausePlayer(player: MediaPlayer, binding: ActivityPlayerBinding)

    fun startPlayer(player: MediaPlayer, binding: ActivityPlayerBinding)
    fun playbackControl(player: MediaPlayer, binding: ActivityPlayerBinding)
}