package com.alchemtech.playlistmaker.domain.api

import android.content.Context

interface CheckInternetConnection {
    fun consume( context: Context): Boolean
}