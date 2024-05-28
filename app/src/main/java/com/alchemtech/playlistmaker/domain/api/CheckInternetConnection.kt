package com.alchemtech.playlistmaker.domain.api

import android.content.Context

interface CheckInternetConnection {
    fun checkConnection( ): Boolean

    interface checkConsumer{
        fun consume(isChecked : Boolean)
    }
}