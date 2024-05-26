package com.alchemtech.playlistmaker.presentation.ui.player

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.ui.UiCalculator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class PlayerFilling(val track: Track, val binding: ActivityPlayerBinding,val context: Context) : UiCalculator {
    init {
        trackArtist()
        trackTimeMillis()
        collectionName()
        releaseDateFill()
        primaryGenreNameFill()
        countryFill()
        playTimeFill()
        albumCoverFill()
        trackTitleFill()
    }

    private fun trackArtist() {
        binding.playerArtistName.text = track.artistName
    }

    private fun trackTimeMillis() {
        binding.trackTimeMillisText.text = track.getTimeString()
    }

    private fun collectionName() {
        if (!track.collectionName.isNullOrEmpty()) {
            binding.collectionNameText.text = track.collectionName
        } else {
            binding.collectionNameText.visibility = View.GONE
            binding.trackAlbum.visibility = View.GONE
        }
    }

    private fun releaseDateFill() {
        binding.releaseDateText.text = track.getReleaseDateString()
    }

    private fun primaryGenreNameFill() {
        binding.primaryGenreNameText.text = track.primaryGenreName
    }

    private fun countryFill() {
        binding.trackCountryText.text = track.country
    }

    private fun playTimeFill() {
        binding.playTime.text = "0:30"
    }

    private fun albumCoverFill() {
        val albumCover: ImageView = binding.playerAlbumCover

        Glide.with(context)
            .load(track.getArtworkUrl512())
            .placeholder(R.drawable.track_album_default_big)
            .centerCrop()
            .transform(
                RoundedCorners(
                    dpToPx(8f, context)
                )
            )
            .into(albumCover)
    }

    private fun trackTitleFill() {
        binding.playerTrackName.text = track.trackName

    }
}