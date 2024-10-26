package com.alchemtech.playlistmaker.domain.entity

data class PlayList(
    val id :Long,
    val name: String,
    val description: String?,
    var coverUri: String?,
    var tracks: List<Track> = listOf(),
)