package com.alchemtech.playlistmaker.presentation.ui.trackCard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.domain.entity.Track

class TrackCardAdapter(private val trackListOf: List<Track>) :
    RecyclerView.Adapter<TrackCardViewHolder>() {

    var onItemClick = { _: Track -> }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TrackCardViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.track_card, viewGroup, false)
        return TrackCardViewHolder(view)
    }

    override fun getItemCount() = trackListOf.size

    override fun onBindViewHolder(viewHolder: TrackCardViewHolder, position: Int) {
        viewHolder.bind(trackListOf[position])
        viewHolder.itemView.setOnClickListener { onItemClick.invoke(trackListOf[position]) }
    }
}
