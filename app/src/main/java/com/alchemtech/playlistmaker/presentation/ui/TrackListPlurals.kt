package com.alchemtech.playlistmaker.presentation.ui

import android.content.Context
import com.alchemtech.playlistmaker.R

interface TrackListPlurals {
    fun Int.convertListSize(context: Context): String {
        return context.resources.getQuantityString( R.plurals.plurals_tracks,this,this)
    }
}