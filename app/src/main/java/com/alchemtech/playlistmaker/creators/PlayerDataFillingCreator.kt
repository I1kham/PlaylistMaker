package com.alchemtech.playlistmaker.creators

import android.content.Context
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.presentation.presenters.PlayerFilling
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.presenters.PlayerFillingImpl

object PlayerDataFillingCreator {
    fun provide( context: Context,binding: ActivityPlayerBinding,track: Track) : PlayerFilling {
      return PlayerFillingImpl(track, binding, context)
    }
}