package com.alchemtech.playlistmaker.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.Track

class TrackSearchAdapter(private val trackListOf : List<Track>) : RecyclerView.Adapter<TrackCardViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TrackCardViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.search_track_card, viewGroup, false)
        return TrackCardViewHolder(view)

    }

    override fun getItemCount() = trackListOf.size

    override fun onBindViewHolder(viewHolder: TrackCardViewHolder, position: Int) {
        viewHolder.bind(trackListOf[position])
    }
}