package com.alchemtech.playlistmaker.search

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.Track
import com.alchemtech.playlistmaker.UiCalculator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.internal.ViewUtils.dpToPx


class TrackCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), UiCalculator {
    private var trackTitle: TextView = itemView.findViewById(R.id.trackTitle)
    private var trackArtist: TextView = itemView.findViewById(R.id.trackArtist)
    private var trackDuration: TextView = itemView.findViewById(R.id.trackDuration)
    private var albumCover: ImageView = itemView.findViewById(R.id.albumCover)

    fun bind(track: Track) {
        trackTitle.text = track.trackTitleStr
        trackArtist.text = track.trackArtistStr
        trackDuration.text = track.trackDurationStr

        val context =

            Glide.with(itemView)
                .load(track.artworkUrl100Str)
                .placeholder(R.drawable.track_album_default)
                .centerCrop()
                .transform(RoundedCorners(dpToPx(2f, itemView.context)))
                .into(albumCover)

    }
}