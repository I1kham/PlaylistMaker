package com.alchemtech.playlistmaker.creators

import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.domain.player.PlayerUseCase

object PlayerCreator {

    fun providePlayer( binding: ActivityPlayerBinding, track: Track): PlayerUseCase {
        return PlayerUseCase(binding,  track)
    }
}