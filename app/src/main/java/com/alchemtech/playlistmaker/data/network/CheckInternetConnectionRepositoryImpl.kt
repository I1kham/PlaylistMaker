package com.alchemtech.playlistmaker.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.alchemtech.playlistmaker.domain.api.CheckInternetConnectionRepository

class CheckInternetConnectionRepositoryImpl(private val context: Context) : CheckInternetConnectionRepository {

    override fun checkConnection(): Boolean {

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (wifiInfo != null && wifiInfo.isConnected) {
            return true
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (wifiInfo != null && wifiInfo.isConnected) {
            return true
        }
        wifiInfo = cm.activeNetworkInfo
        return wifiInfo != null && wifiInfo.isConnected
    }
}