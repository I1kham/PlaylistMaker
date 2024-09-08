package com.alchemtech.playlistmaker.data.converters

import com.alchemtech.playlistmaker.data.db.entity.PlayListEntity
import com.alchemtech.playlistmaker.domain.entity.PlayList

class PlayListDbConvertor(private val tracksStringConvertor:TracksStringConvertor) {
    fun map(playlist: PlayList): PlayListEntity {
        return PlayListEntity(
            name = playlist.name,
            description = playlist.description,
            coverUri = playlist.coverUri,
            tracks = tracksStringConvertor.map(playlist.tracks)
        )
    }

    fun map(playlist: PlayListEntity): PlayList {
        return PlayList(
            name = playlist.name,
            description = playlist.description,
            coverUri = playlist.coverUri,
            tracks = tracksStringConvertor.map(playlist.tracks)
        )
    }

}