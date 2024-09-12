package com.alchemtech.playlistmaker.presentation.ui.playLikstBottomCard

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.util.TypedValueCompat.dpToPx
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.domain.entity.PlayList
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class PlayListBottomCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val image: ImageView = itemView.findViewById(R.id.PlCover)
    private val title: TextView = itemView.findViewById(R.id.plName)
    private val description: TextView = itemView.findViewById(R.id.plDescription)

    fun bind(playList: PlayList) {
        title.text = playList.name
        description.text = playList.tracks.size.convertListSize()

        Glide.with(itemView.context)
            .load(playList.coverUri)
            .placeholder(R.drawable.track_album_default_big)
            .fitCenter()
            .transform(
                RoundedCorners(
                    dpToPx(8f, itemView.context.resources.displayMetrics).toInt()
                )
            )
            .into(image)
    }

    private fun Int.convertListSize(): String {
        return when (this.getLastDigit(2)) {
            11, 12, 13, 14 -> "$this треков"
            else -> {
                return when (this.getLastDigit(1)) {
                    0 -> "$this треков"
                    1 -> "$this трек"
                    2, 3, 4 -> "$this трека"
                    5, 6, 7, 8, 9 -> "$this треков"
                    else -> {
                        "error in <PlayListCardViewHolder>"
                    }
                }
            }

        }
    }

    private fun Int.getLastDigit(last: Int): Int {
        var d = 10
        if (last > 1) {
            for (i in 1 until last) {
                d *= 10
            }
        }
        return this - (this / d) * d
    }
}