package com.alchemtech.playlistmaker.data.converters

import androidx.core.net.toUri
import com.alchemtech.playlistmaker.data.db.entity.PlayListEntity
import com.alchemtech.playlistmaker.domain.entity.PlayList

class PlayListDbConvertor(private val tracksStringConvertor: TracksStringConvertor) {
    fun map(playlist: PlayList): PlayListEntity {
        return PlayListEntity(
            playListId = playlist.id,
            name = playlist.name,
            description = playlist.description,
            coverUri = playlist.coverUri.toString(),
            tracks = tracksStringConvertor.map(playlist.tracks)
        )
    }

    fun map(playlist: PlayListEntity): PlayList {
        return PlayList(
            id = playlist.playListId,
            name = playlist.name,
            description = playlist.description,
            coverUri = playlist.coverUri?.toUri(),
            tracks = tracksStringConvertor.map(playlist.tracks)
        )
    }

}