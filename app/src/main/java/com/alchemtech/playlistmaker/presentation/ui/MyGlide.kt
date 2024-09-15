package com.alchemtech.playlistmaker.presentation.ui

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.alchemtech.playlistmaker.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

interface MyGlide : UiCalculator {
    fun imageViewFillBig(uri: Uri?, imageView: ImageView?, context: Context) {
        if (imageView != null) {
            Glide.with(context)
                .load(uri)
                .placeholder(R.drawable.track_album_default_big)
                .fitCenter()
                .transform(
                    RoundedCorners(
                        dpToPx(8f, context)
                    )
                )
                .into(imageView)
        }
    }

    fun imageViewFillSmall(uri: Uri?, imageView: ImageView?, context: Context) {
        if (imageView != null) {
            Glide.with(context)
                .load(uri)
                .placeholder(R.drawable.track_album_default)
                .centerCrop()
                .transform(
                    RoundedCorners(
                        dpToPx(8f, context)
                    )
                )
                .into(imageView)
        }
    }

    fun imageViewFillBigNoPlaceHolder(uri: Uri?, imageView: ImageView?, context: Context) {
        if (imageView != null) {
            Glide.with(context)
                .load(uri)
                .fitCenter()
                .transform(
                    RoundedCorners(
                        dpToPx(8f, context)
                    )
                )
                .into(imageView)
        }
    }

}