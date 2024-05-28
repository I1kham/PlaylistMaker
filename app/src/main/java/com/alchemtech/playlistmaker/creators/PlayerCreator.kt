package com.alchemtech.playlistmaker.creators

import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.domain.player.PlayerInteractor
import com.alchemtech.playlistmaker.domain.player.PlayerUseCase

object PlayerCreator {

    fun providePlayer( binding: ActivityPlayerBinding, track: Track): PlayerInteractor {
        return PlayerUseCase(binding,  track)
    }
}