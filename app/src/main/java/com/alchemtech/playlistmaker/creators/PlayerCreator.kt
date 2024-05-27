package com.alchemtech.playlistmaker.creators

import android.content.Context
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.domain.impl.PlayerInteractorImpl

object PlayerCreator {

    fun providePlayer( binding: ActivityPlayerBinding,  context: Context,  track: Track): PlayerInteractorImpl {
        return PlayerInteractorImpl(binding,  context,  track)
    }
}