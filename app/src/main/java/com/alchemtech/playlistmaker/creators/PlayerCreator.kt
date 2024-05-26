package com.alchemtech.playlistmaker.creators

import android.content.Context
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.domain.player.PlayerInteractorImplUseCase

object PlayerCreator {

    fun providePlayer( binding: ActivityPlayerBinding,  context: Context,  track: Track): PlayerInteractorImplUseCase {
        return PlayerInteractorImplUseCase(binding,  context,  track)
    }
}