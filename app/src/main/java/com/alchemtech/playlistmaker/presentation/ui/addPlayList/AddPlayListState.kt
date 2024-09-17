package com.alchemtech.playlistmaker.presentation.ui.addPlayList

import android.net.Uri

sealed interface AddPlayListState {
    data object Loading : AddPlayListState
    data object Exit : AddPlayListState
    data class SetPic (val uri : Uri?): AddPlayListState
}