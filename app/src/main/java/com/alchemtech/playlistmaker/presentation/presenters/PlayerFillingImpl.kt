package com.alchemtech.playlistmaker.presentation.presenters

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.ui.TrackUtils.getArtworkUrl512
import com.alchemtech.playlistmaker.presentation.ui.TrackUtils.getReleaseDateString
import com.alchemtech.playlistmaker.presentation.ui.TrackUtils.getTimeString
import com.alchemtech.playlistmaker.presentation.ui.UiCalculator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class PlayerFillingImpl(private val track: Track, private val binding: ActivityPlayerBinding, private val context: Context) : PlayerFilling, UiCalculator {
    init {
start()
    }
    override fun start() {
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
      //  binding.playTime.text = "0:30"
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