package com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.state

import com.alchemtech.playlistmaker.domain.entity.PlayList

sealed interface PlayListsState {
    data class ShowList(val playLists: List<PlayList>) : PlayListsState
    data object EmptyList : PlayListsState
    data object Loading : PlayListsState
}