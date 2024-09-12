package com.alchemtech.playlistmaker.presentation.ui.playLikstBottomCard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.domain.entity.PlayList

class PlayListBottomCardAdapter(private val playLists: List<PlayList>): RecyclerView.Adapter<PlayListBottomCardViewHolder>() {
    var onItemClick = { _: PlayList -> }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListBottomCardViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.play_list_bottom_sheet_card, parent, false)
            return PlayListBottomCardViewHolder(view)
        }

        override fun getItemCount(): Int {
            return playLists.size
        }

        override fun onBindViewHolder(holder: PlayListBottomCardViewHolder, position: Int) {
            holder.bind(playLists[position])
            holder.itemView.setOnClickListener { onItemClick.invoke(playLists[position]) }
        }
    }