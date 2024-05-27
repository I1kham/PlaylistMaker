package com.alchemtech.playlistmaker.creators

import android.content.Context
import com.alchemtech.playlistmaker.data.network.CheckInternetConnectionImpl

object InternetCheckCreator {
    fun provideInternetCheck(context: Context): Boolean {

        return CheckInternetConnectionImpl().consume(context)
    }
}