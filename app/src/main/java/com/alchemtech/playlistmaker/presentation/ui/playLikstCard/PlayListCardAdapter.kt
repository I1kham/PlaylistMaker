package com.alchemtech.playlistmaker.presentation.ui.playLikstCard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.domain.entity.PlayList

class PlayListCardAdapter(private val playLists: List<PlayList>): RecyclerView.Adapter<PlayListCardViewHolder>() {
    var onItemClick = { _: PlayList -> }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListCardViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.play_list_card, parent, false)
            return PlayListCardViewHolder(view)
        }

        override fun getItemCount(): Int {
            return playLists.size
        }

        override fun onBindViewHolder(holder: PlayListCardViewHolder, position: Int) {
            holder.bind(playLists[position])
            holder.itemView.setOnClickListener { onItemClick.invoke(playLists[position]) }
        }
    }