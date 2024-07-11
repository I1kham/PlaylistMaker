package com.alchemtech.playlistmaker.domain.api

interface PlayerRepository {

    fun pause()
    fun start()
    fun preparePlayer(
        onPreparedListenerConsumer: OnPreparedListenerConsumer,
        onCompletionListenerConsumer: OnCompletionListenerConsumer,
        source: String
    )

    fun release()

    fun duration() : Int
    fun playerIsPlaying() :Boolean
    fun currentPosition(): Int
    fun isPrepared():Boolean
    fun interface OnPreparedListenerConsumer {
        fun consume()
    }

    fun interface OnCompletionListenerConsumer {
        fun consume()

    }
}