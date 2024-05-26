package com.alchemtech.playlistmaker.creators

import android.content.Context
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.presenters.PlayerFilling

object PlayerDataFillingCreator {
    fun provide( context: Context,binding: ActivityPlayerBinding,track: Track) : PlayerFilling {
      return PlayerFilling(track, binding, context)
    }
}