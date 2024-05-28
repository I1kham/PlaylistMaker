package com.alchemtech.playlistmaker.domain.player

/* объект заготовка */
interface PlayerInteractor {
    var playerState : Int

    fun pausePlayer()

    fun startPlayer()
    fun playbackControl()

    fun preparePlayer()
    fun release()

}