package com.alchemtech.playlistmaker.presentation.ui

import com.alchemtech.playlistmaker.domain.entity.Track
import java.text.SimpleDateFormat
import java.util.Locale

    fun Track.getArtworkUrl512(): String {
        return if (artworkUrl100!!.isNotEmpty()) {
            artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")
        } else {
            ""
        }
    }

    fun Track.getTimeString(): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTimeMillis)
    }

    fun Track.getReleaseDateString(): String {
        return if (releaseDate.isNullOrEmpty()) {
            ""
        } else releaseDate.substring(0 until 4) + " год"
    }