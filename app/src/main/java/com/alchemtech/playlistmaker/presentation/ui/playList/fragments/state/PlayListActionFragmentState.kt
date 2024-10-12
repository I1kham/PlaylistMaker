package com.alchemtech.playlistmaker.presentation.ui.playList.fragments.state

import com.alchemtech.playlistmaker.domain.entity.PlayList

sealed interface PlayListActionFragmentState {
    data class Content(val playList: PlayList?) : PlayListActionFragmentState
    data object Exit : PlayListActionFragmentState
}