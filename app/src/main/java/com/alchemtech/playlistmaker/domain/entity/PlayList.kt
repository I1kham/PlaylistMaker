package com.alchemtech.playlistmaker.domain.entity

import android.net.Uri

data class PlayList(
    val id :Long,
    val name: String,
    val description: String?,
    var coverUri: Uri?,
    var tracks: List<Track> = listOf(),
)