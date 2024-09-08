package com.alchemtech.playlistmaker.domain.entity

class PlayList(
    val name: String,
    val description: String?,
    val coverUri: String?,
    val tracks: List<Track> = listOf(),
)
//{todo
//    fun tracksCount() = tracks.size
//}