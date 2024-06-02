package com.alchemtech.playlistmaker.presentation.ui

import com.alchemtech.playlistmaker.domain.entity.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Locale

object TrackUtils {

    fun Track.getArtworkUrl512(): Any {
        return artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")
    }

    fun Track.getTimeString(): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTimeMillis)
    }

    fun Track.getReleaseDateString(): String {
        return releaseDate.substring(0 until 4) + " год"
    }

    fun Track.convertToString(): String {
        return Gson().toJson(this)
    }

    fun convertFromString(string: String): Track {
        return Gson().fromJson<Track>(
            string,
            object : TypeToken<Track?>
                () {}.type
        ) as Track
    }
}