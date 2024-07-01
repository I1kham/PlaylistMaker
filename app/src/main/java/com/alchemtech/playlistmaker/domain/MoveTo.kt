package com.alchemtech.playlistmaker.domain

import com.alchemtech.playlistmaker.domain.entity.Track

interface MoveTo {
    fun toPlayer(track: Track)
    fun toSearch()
    fun toMediaLib()
    fun toSettings()
}