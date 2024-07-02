package com.alchemtech.playlistmaker.creators

import com.alchemtech.playlistmaker.data.repository.PlayerRepositoryImpl
import com.alchemtech.playlistmaker.domain.api.PlayerRepository
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.domain.player.PlayerInteractor
import com.alchemtech.playlistmaker.domain.player.PlayerInteractorImlp

object PlayerCreator {

    fun providePlayer(track: Track): PlayerInteractor {
        return PlayerInteractorImlp( providePlayerRepository(track.previewUrl!!))
    }

    private fun providePlayerRepository(previewUrl :String):PlayerRepository{
        return PlayerRepositoryImpl(previewUrl)
    }
}