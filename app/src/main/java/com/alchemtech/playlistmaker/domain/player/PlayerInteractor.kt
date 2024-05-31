package com.alchemtech.playlistmaker.domain.player

interface PlayerInteractor {
    fun pausePlayer()

    fun startPlayer()
    fun playbackControl()
    fun release()

}