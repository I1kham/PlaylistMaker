package com.alchemtech.playlistmaker.data.converters

import com.alchemtech.playlistmaker.data.db.entity.PlayListEntity
import com.alchemtech.playlistmaker.domain.entity.PlayList
import com.alchemtech.playlistmaker.domain.entity.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

class PlayListDbConvertor(private val gson: Gson) {
    fun map(playlist: PlayList): PlayListEntity {
        return PlayListEntity(
            name = playlist.name,
            description = playlist.description,
            coverUri = playlist.coverUri,
            tracks = map(playlist.tracks)
        )
    }

    fun map(playlist: PlayListEntity): PlayList {
        return PlayList(
            name = playlist.name,
            description = playlist.description,
            coverUri = playlist.coverUri,
            tracks = map(playlist.tracks)
        )
    }

    fun map(tracksList: List<Track>): String {
        return gson.toJson(tracksList as Serializable)
    }

    fun map(tracksList: String?): List<Track> {
        val json = tracksList
            ?: return emptyList()
        return gson.fromJson<Track>(
            json,
            object : TypeToken<List<Track?>?>
                () {}.type
        ) as List<Track>
    }

}