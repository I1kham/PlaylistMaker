package com.alchemtech.playlistmaker.presentation.ui.playList.fragments.state

import com.alchemtech.playlistmaker.domain.entity.PlayList

interface PlayListActionFragmentState {
    data class Content(val playList: PlayList?) : PlayListActionFragmentState
}