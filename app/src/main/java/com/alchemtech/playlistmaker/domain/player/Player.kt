package com.alchemtech.playlistmaker.domain.player

import android.media.MediaPlayer
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.domain.models.Track

/* объект заготовка */
interface Player {
    var playerState : Int

    fun pausePlayer(player: MediaPlayer, binding: ActivityPlayerBinding)

    fun startPlayer(player: MediaPlayer, binding: ActivityPlayerBinding)
    fun playbackControl(player: MediaPlayer, binding: ActivityPlayerBinding)

    fun preparePlayer(player: MediaPlayer, binding: ActivityPlayerBinding,track: Track)
}