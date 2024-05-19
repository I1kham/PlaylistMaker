package com.alchemtech.playlistmaker.domain.api

interface TrackDtoForTrackReformat {
    fun getArtworkUrl512(artworkUrl100: String): String

    fun getFormattedTrackTimeMillis(trackTimeMillis: String): String

    fun getFormattedTrackReleaseDate(releaseDate: String) :String

}