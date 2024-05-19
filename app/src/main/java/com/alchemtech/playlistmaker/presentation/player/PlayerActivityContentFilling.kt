package com.alchemtech.playlistmaker.presentation.player

import android.view.View
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.domain.models.Track

class PlayerActivityContentFilling (track : Track, binding: ActivityPlayerBinding) {

    private fun trackArtist(track: Track, binding: ActivityPlayerBinding) {
        binding.playerArtistName.text= track.artistName
    }

    private fun trackTimeMillis(track: Track, binding: ActivityPlayerBinding) {
        binding.trackTimeMillisText.text = track.trackTimeMillis
    }

    private fun collectionName(track: Track, binding: ActivityPlayerBinding) {
        if (track.collectionName.isNullOrEmpty()) {
            binding.collectionNameText.visibility = View.GONE
           binding.trackAlbum.visibility = View.GONE
        } else {
            binding.collectionNameText.text = track.collectionName
        }
    }

    private fun releaseDateFill(track: Track, binding: ActivityPlayerBinding) {
        binding.releaseDateText.text = track.releaseDate
    }

    private fun primaryGenreNameFill (track: Track, binding: ActivityPlayerBinding){
        binding.primaryGenreNameText.text = track.primaryGenreName
    }

    private fun countryFill(track: Track, binding: ActivityPlayerBinding) {
        binding.trackCountryText.text = track.country
    }

//    private fun albumCoverFill(track: Track, binding: ActivityPlayerBinding) {
//        val albumCover = binding.playerAlbumCover
//        Glide.with(albumCover)
//            .load(track.artworkUrl512)
//            .placeholder(R.drawable.track_album_default_big)
//            .centerCrop()
//            .transform(
//                RoundedCorners(
//                    getPxFromDp(8f)
//                )
//            )
//            .into(albumCover)
//    }

    private fun trackTitleFill(track: Track, binding: ActivityPlayerBinding) {
        binding.playerTrackName.text = track.trackName
    }
}