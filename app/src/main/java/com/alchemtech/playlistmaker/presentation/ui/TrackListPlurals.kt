package com.alchemtech.playlistmaker.presentation.ui

import android.content.Context
import com.alchemtech.playlistmaker.R

    fun Int.convertListPlurals(context: Context): String {
        return context.resources.getQuantityString( R.plurals.plurals_tracks,this,this)
    }

fun Int.convertDurationPlurals(context: Context): String {
    return context.resources.getQuantityString( R.plurals.plurals_minutes,this,this)
}