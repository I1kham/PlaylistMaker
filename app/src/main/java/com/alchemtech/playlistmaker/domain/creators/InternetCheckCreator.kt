package com.alchemtech.playlistmaker.domain.creators

import android.content.Context
import com.alchemtech.playlistmaker.data.network.CheckInternetConnectionImpl

class InternetCheckCreator(context: Context) {
    val isChecked = checked(context)


    private fun checked(context: Context): Boolean {
        return CheckInternetConnectionImpl(context).hasInternetConnection()
    }
}