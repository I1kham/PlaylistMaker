package com.alchemtech.playlistmaker.presentation.ui.player.model

import com.alchemtech.playlistmaker.domain.entity.Track

sealed interface PlayerState {
    data class Play(val track: Track) : PlayerState
    data class Pause(val track: Track) : PlayerState
    data class OnPrepared(val track: Track) : PlayerState
    data class OnCompletion(val track: Track) : PlayerState
    data class Fill(val track: Track) : PlayerState
}