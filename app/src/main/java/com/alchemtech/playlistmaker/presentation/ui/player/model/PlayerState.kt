package com.alchemtech.playlistmaker.presentation.ui.player.model

import com.alchemtech.playlistmaker.domain.entity.Track

sealed interface PlayerState {
    data object Play : PlayerState
    data object Pause : PlayerState
    data object FillViewWithTrackData : PlayerState
    data class OnPrepared(val track: Track) : PlayerState
    data object OnCompletion : PlayerState
    data class SetPlayTime (val position : String) : PlayerState
    data object BackBut : PlayerState
    data class Fill(val playBut : Unit, val backBut : Unit, val track: Track)

}