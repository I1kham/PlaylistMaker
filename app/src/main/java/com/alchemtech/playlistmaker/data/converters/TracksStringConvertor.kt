package com.alchemtech.playlistmaker.data.converters

import com.alchemtech.playlistmaker.domain.entity.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

class TracksStringConvertor(private val gson: Gson) {
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


    fun mapListToIDs(tracks: List<Track>): String {
        val idsList = mutableListOf<String>()
        tracks.map { track: Track -> idsList.add(track.trackId) }
        return gson.toJson(idsList.toList() as Serializable)
    }

    fun mapIDsListToList(idsList: String?): List<String> {
        val json = idsList
            ?: return emptyList()
        return gson.fromJson<String>(
            json,
            object : TypeToken<List<String?>?>
                () {}.type
        ) as List<String>
    }
}