package com.alchemtech.playlistmaker.presentation.ui.addPlayList

import android.net.Uri
import com.alchemtech.playlistmaker.domain.entity.PlayList

sealed interface AddPlayListState {
    data object Loading : AddPlayListState
    data object Exit : AddPlayListState
    data class SetPic (val uri : Uri?): AddPlayListState
    data class Content(val playList: PlayList): AddPlayListState
}