package com.alchemtech.playlistmaker.data.dto

import com.alchemtech.playlistmaker.domain.api.TrackDtoForTrackReformat
import java.text.SimpleDateFormat
import java.util.Locale

open class TrackDtoForTrackReformatImpl: TrackDtoForTrackReformat {
    override fun getArtworkUrl512(artworkUrl100 : String ): String {
        return artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")
    }

    override fun getFormattedTrackTimeMillis(trackTimeMillis: String): String {
       return SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTimeMillis)
    }

    override fun getFormattedTrackReleaseDate(releaseDate: String): String {
        return releaseDate.substring(0 until 4) + " год"
    }
}