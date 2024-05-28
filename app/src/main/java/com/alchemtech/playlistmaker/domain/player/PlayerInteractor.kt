package com.alchemtech.playlistmaker.domain.player

interface PlayerInteractor {
    var playerState: Int

    fun pausePlayer()

    fun startPlayer()
    fun playbackControl()
    fun release()

}