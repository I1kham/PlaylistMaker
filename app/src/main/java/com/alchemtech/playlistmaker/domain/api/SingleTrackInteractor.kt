package com.alchemtech.playlistmaker.domain.api

import com.alchemtech.playlistmaker.domain.entity.Track

interface SingleTrackInteractor {

    fun writeTrack(track: Track)
    fun readTrack() : Track?
}