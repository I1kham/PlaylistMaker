package com.alchemtech.playlistmaker.presentation.ui.player.model

import com.alchemtech.playlistmaker.domain.entity.PlayList

sealed interface AddTrackToPlayListFragmentState {

    data class ShowList(val tracks: List<PlayList>) : AddTrackToPlayListFragmentState

    data object Empty : AddTrackToPlayListFragmentState

    data class TrackAdded(val added: Boolean, val namePlayList: String) :
        AddTrackToPlayListFragmentState

    data class Loading(val isLoading: Boolean) : AddTrackToPlayListFragmentState
}