package com.alchemtech.playlistmaker.presentation.ui.playList

sealed interface PlayListFragmentState {
    data class Content(
        val playListCover: String?,
        val name: String,
        val description: String?,
        val duration: Long,
        val count: Int,
    ) : PlayListFragmentState

    data class Message(val message: String) : PlayListFragmentState
    data class DelMessage(val message: String) : PlayListFragmentState
    data object Exit : PlayListFragmentState
}