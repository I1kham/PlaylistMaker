package com.alchemtech.playlistmaker.presentation.ui.playList

import android.net.Uri

sealed interface PlayListFragmentState {
    data class Content(
        val playListCover: Uri?,
        val name: String,
        val description: String?,
        val duration: Long,
        val count: Int,
    ) : PlayListFragmentState

    data class Deleted(val message: String) : PlayListFragmentState
    data object Exit : PlayListFragmentState
}