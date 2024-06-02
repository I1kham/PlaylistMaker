package com.alchemtech.playlistmaker.domain.api

interface CheckInternetConnectionRepository {
    fun checkConnection( ): Boolean

}