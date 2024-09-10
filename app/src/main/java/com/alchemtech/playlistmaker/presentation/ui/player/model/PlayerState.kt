package com.alchemtech.playlistmaker.presentation.ui.player.model

import com.alchemtech.playlistmaker.domain.entity.Track

sealed interface PlayerState {
    data object Play : PlayerState
    data object Pause : PlayerState
    data object OnPrepared : PlayerState
    data object OnCompletion : PlayerState
    data class Fill(val track: Track) : PlayerState
    data class LikeBut(val isFavorite: Boolean): PlayerState
}