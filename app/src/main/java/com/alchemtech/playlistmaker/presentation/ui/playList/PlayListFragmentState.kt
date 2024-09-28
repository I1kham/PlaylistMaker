package com.alchemtech.playlistmaker.presentation.ui.playList

import com.alchemtech.playlistmaker.domain.entity.PlayList

interface PlayListFragmentState{
    data class Content(val playList: PlayList): PlayListFragmentState
}