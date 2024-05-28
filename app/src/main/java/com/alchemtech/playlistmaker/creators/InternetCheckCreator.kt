package com.alchemtech.playlistmaker.creators

import android.content.Context
import com.alchemtech.playlistmaker.domain.api.CheckInternetConnection
import com.alchemtech.playlistmaker.data.network.CheckInternetConnectionImpl

object InternetCheckCreator {
    fun provideInternetCheck(context: Context): CheckInternetConnection {

        return CheckInternetConnectionImpl(context)
    }
}