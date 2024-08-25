package com.alchemtech.playlistmaker.presentation.ui.player.model

import com.alchemtech.playlistmaker.domain.entity.Track

sealed interface PlayerState {
    object Play : PlayerState
    object Pause : PlayerState
    object OnPrepared : PlayerState
    object OnCompletion : PlayerState
    data class Fill(val track: Track) : PlayerState
}