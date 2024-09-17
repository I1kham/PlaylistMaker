package com.alchemtech.playlistmaker.presentation.ui.player

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.ui.fillByUriOrPlaceHolder
import com.alchemtech.playlistmaker.presentation.ui.getArtworkUrl512
import com.alchemtech.playlistmaker.presentation.ui.getReleaseDateString
import com.alchemtech.playlistmaker.presentation.ui.getTimeString

interface PlayerStringsFilling  {

    fun fillAll(track: Track, binding: ActivityPlayerBinding?, context: Context) {
        playTimeFill(track, binding)
        fill(track, binding, context)
    }

    fun fill(track: Track, binding: ActivityPlayerBinding?, context: Context) {
        trackArtist(track, binding)
        trackTimeMillis(track, binding)
        collectionName(track, binding)
        releaseDateFill(track, binding)
        primaryGenreNameFill(track, binding)
        countryFill(track, binding)
        albumCoverFill(track, binding, context)
        trackTitleFill(track, binding)
    }

    private fun trackArtist(track: Track, binding: ActivityPlayerBinding?) {
        binding?.playerArtistName?.text = track.artistName
    }

    private fun trackTimeMillis(track: Track, binding: ActivityPlayerBinding?) {
        binding?.trackCurrentPosition?.text = track.getTimeString()
    }

    private fun collectionName(track: Track, binding: ActivityPlayerBinding?) {
        if (!track.collectionName.isNullOrEmpty()) {
            binding?.collectionNameText?.text = track.collectionName
        } else {
            binding?.collectionNameText?.visibility = View.GONE
            binding?.trackAlbum?.visibility = View.GONE
        }
    }

    private fun releaseDateFill(track: Track, binding: ActivityPlayerBinding?) {
        if (track.getReleaseDateString().isNotEmpty()) {
            binding?.releaseDateText?.text = track.getReleaseDateString()
        } else {
            binding?.releaseDateText?.visibility = View.GONE
            binding?.releaseDate?.visibility = View.GONE
        }
    }

    private fun primaryGenreNameFill(track: Track, binding: ActivityPlayerBinding?) {
        binding?.primaryGenreNameText?.text = track.primaryGenreName
    }

    private fun countryFill(track: Track, binding: ActivityPlayerBinding?) {
        binding?.trackCountryText?.text = track.country
    }

    private fun playTimeFill(track: Track, binding: ActivityPlayerBinding?) {
        binding?.playTime?.text = "0:30"
    }

    private fun albumCoverFill(track: Track, binding: ActivityPlayerBinding?, context: Context) {
        val albumCover: ImageView? = binding?.playerAlbumCover
        albumCover?.fillByUriOrPlaceHolder(track.getArtworkUrl512().toUri(),context) }

    private fun trackTitleFill(track: Track, binding: ActivityPlayerBinding?) {
        binding?.playerTrackName?.text = track.trackName
    }
}