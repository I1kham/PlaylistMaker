package com.alchemtech.playlistmaker.presentation.ui.player.model

sealed interface PlayerState {
    data object Play : PlayerState
    data object Pause : PlayerState
    data object FillViewWithTrackData : PlayerState
    data object OnPrepared : PlayerState
    data object OnCompletion : PlayerState
    data class SetPlayTime (val position : String) : PlayerState
    data object BackBut : PlayerState
}