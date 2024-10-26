package com.alchemtech.playlistmaker.presentation.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.alchemtech.playlistmaker.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.ViewTarget

fun ImageView.fillBy(
    uri: String?,
    context: Context,
    smallCorners: Boolean? = false,
): ViewTarget<ImageView, Drawable> {
    return Glide.with(context)
        .load(uri)
        .fitCenter()
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .transform(
            RoundedCorners(
                dpToPx(cornersSize(smallCorners), context)
            )
        )
        .into(this)
}

fun ImageView.fillByUriOrPlaceHolder(
    uri: String?,
    context: Context,
    smallCorners: Boolean? = false,
): ViewTarget<ImageView, Drawable> {
    return Glide.with(context)
        .load(uri)
        .placeholder(R.drawable.track_album_default)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .fitCenter()
        .transform(
            RoundedCorners(
                dpToPx(cornersSize(smallCorners), context)
            )
        )
        .into(this)
}

fun ImageView.fillByUriOrPlaceHolderNoCorners(
    uri: String?,
    context: Context,
): ViewTarget<ImageView, Drawable> {
    return Glide.with(context)
        .load(uri)
        .placeholder(R.drawable.track_album_default)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .fitCenter()
        .into(this)

}
private fun cornersSize(smallCorners: Boolean?): Float {
    return if (smallCorners == null || smallCorners == false) {
        8f
    } else {
        2f
    }
}

