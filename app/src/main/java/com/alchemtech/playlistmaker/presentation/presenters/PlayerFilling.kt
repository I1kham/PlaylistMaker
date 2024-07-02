package com.alchemtech.playlistmaker.presentation.presenters

import com.alchemtech.playlistmaker.domain.entity.Track

interface PlayerFilling {
    fun fill ( track: Track)
}