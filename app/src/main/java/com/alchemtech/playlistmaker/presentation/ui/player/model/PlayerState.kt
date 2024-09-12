package com.alchemtech.playlistmaker.presentation.ui.player.model

import com.alchemtech.playlistmaker.domain.entity.PlayList
import com.alchemtech.playlistmaker.domain.entity.Track

sealed interface PlayerState {
    data class Play(val track: Track) : PlayerState
    data class Pause(val track: Track) : PlayerState
    data class OnPrepared(val track: Track) : PlayerState
    data class OnCompletion(val track: Track) : PlayerState
    data class Fill(val track: Track) : PlayerState
    data class LikeBut(val track: Track): PlayerState
    data class ShowList(val playLists: List<PlayList>) : PlayerState
    data object EmptyList : PlayerState
    data class TrackAdded(val added: Boolean) : PlayerState
    data object LoadingAdd : PlayerState

}