package com.alchemtech.playlistmaker.presentation.ui.trackCard

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.ui.TrackUtils.getTimeString
import com.alchemtech.playlistmaker.presentation.ui.UiCalculator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


class TrackCardViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView), UiCalculator {
    private var trackTitle: TextView = itemView.findViewById(R.id.trackTitle)
    private var trackArtist: TextView = itemView.findViewById(R.id.trackArtist)
    private var trackDuration: TextView = itemView.findViewById(R.id.trackDuration)
    private var albumCover: ImageView = itemView.findViewById(R.id.albumCover)

    fun bind(track: Track) {
        trackTitle.text = track.trackName
        trackArtist.text = track.artistName
        trackDuration.text = track.getTimeString()

        val context =
            Glide.with(itemView)
                .load(track.artworkUrl100)
                .placeholder(R.drawable.track_album_default)
                .centerCrop()
                .transform(RoundedCorners(dpToPx(2f, itemView.context)))
                .into(albumCover)
    }
}