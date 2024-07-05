package com.alchemtech.playlistmaker.domain

interface Navigator {
    fun toPlayer()
    fun toSearch()
    fun toMediaLib()
    fun toSettings()
}