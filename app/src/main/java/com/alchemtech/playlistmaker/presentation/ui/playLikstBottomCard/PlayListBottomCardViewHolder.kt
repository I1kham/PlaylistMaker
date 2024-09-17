package com.alchemtech.playlistmaker.presentation.ui.playLikstBottomCard

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.domain.entity.PlayList
import com.alchemtech.playlistmaker.presentation.ui.convertListSize
import com.alchemtech.playlistmaker.presentation.ui.fillByUriOrPlaceHolder

class PlayListBottomCardViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val image: ImageView = itemView.findViewById(R.id.PlCover)
    private val title: TextView = itemView.findViewById(R.id.plName)
    private val description: TextView = itemView.findViewById(R.id.plDescription)

    fun bind(playList: PlayList) {
        title.text = playList.name
        description.text = playList.tracks.size.convertListSize(itemView.context)
        image.fillByUriOrPlaceHolder(playList.coverUri, itemView.context,true)
    }
}