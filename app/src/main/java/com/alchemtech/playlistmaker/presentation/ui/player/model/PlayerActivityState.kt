package com.alchemtech.playlistmaker.presentation.ui.player.model

sealed interface PlayerActivityState {
    data object Play : PlayerActivityState
    data object Pause : PlayerActivityState
    data object FillViewWithTrackData : PlayerActivityState
    data object OnPrepared : PlayerActivityState
    data object OnCompletion : PlayerActivityState
    data class SetPlayTime (val position : String) : PlayerActivityState
}