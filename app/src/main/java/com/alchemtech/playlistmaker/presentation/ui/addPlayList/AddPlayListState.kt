package com.alchemtech.playlistmaker.presentation.ui.addPlayList

import com.alchemtech.playlistmaker.domain.entity.PlayList

sealed interface AddPlayListState {
    data object Loading : AddPlayListState
    data class Exit(val message: String) : AddPlayListState
    data class SetPic(val uri: String?) : AddPlayListState
    data class Content(val playList: PlayList) : AddPlayListState
}