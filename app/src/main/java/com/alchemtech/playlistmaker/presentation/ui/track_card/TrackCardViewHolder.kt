package com.alchemtech.playlistmaker.presentation.ui.track_card

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.ui.getTimeString
import com.alchemtech.playlistmaker.presentation.ui.imageViewFillSmall


class TrackCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var trackTitle: TextView = itemView.findViewById(R.id.plName)
    private var trackArtist: TextView = itemView.findViewById(R.id.plDescription)
    private var trackDuration: TextView = itemView.findViewById(R.id.trackDuration)
    private var albumCover: ImageView = itemView.findViewById(R.id.albumCover)

    fun bind(track: Track) {
        trackTitle.text = track.trackName
        trackArtist.text = track.artistName
        trackDuration.text = track.getTimeString()
        imageViewFillSmall(track.artworkUrl100?.toUri(), albumCover, itemView.context)
    }
}