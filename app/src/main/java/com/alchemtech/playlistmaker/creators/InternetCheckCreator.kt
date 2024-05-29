package com.alchemtech.playlistmaker.creators

import android.content.Context
import com.alchemtech.playlistmaker.data.network.CheckInternetConnectionImpl
import com.alchemtech.playlistmaker.domain.api.CheckInternetConnection

object InternetCheckCreator {
    fun provideInternetCheck(context: Context): CheckInternetConnection {

        return CheckInternetConnectionImpl(context)
    }
}