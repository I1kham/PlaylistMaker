package com.alchemtech.playlistmaker.presentation.ui.playLikstCard

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.util.TypedValueCompat.dpToPx
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.domain.entity.PlayList
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class PlayListCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {


    private val image: ImageView = itemView.findViewById(R.id.PlCover)
    private val title: TextView = itemView.findViewById(R.id.plName)
    private val description: TextView = itemView.findViewById(R.id.plDescription)

    fun bind(playList: PlayList) {
        title.text = playList.name
        description.text = playList.description

        val albumCover: ImageView = image

            Glide.with(itemView.context)
                .load(playList.coverUri)
                .placeholder(R.drawable.track_album_default_big)
                .centerCrop()
                .transform(
                    RoundedCorners(
                        dpToPx(8f, itemView.context.resources.displayMetrics).toInt()
                    )
                )
                .into(albumCover)


        }
    }