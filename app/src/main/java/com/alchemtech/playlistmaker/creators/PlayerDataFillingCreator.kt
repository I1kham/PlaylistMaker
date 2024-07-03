package com.alchemtech.playlistmaker.creators

import android.content.Context
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.presentation.presenters.PlayerFilling
import com.alchemtech.playlistmaker.presentation.presenters.PlayerFillingImpl

object PlayerDataFillingCreator {

    fun provide(binding: ActivityPlayerBinding, context: Context): PlayerFilling {
        return PlayerFillingImpl(binding, context)
    }
}