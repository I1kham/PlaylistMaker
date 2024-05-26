package com.alchemtech.playlistmaker.domain.db

import android.content.Context
import com.alchemtech.playlistmaker.data.dto.repository.SharedPrefInteractorImpl
import com.alchemtech.playlistmaker.domain.entity.Track
import java.io.Serializable

class ListTrackDbInteractor(val context: Context) : SharedPrefInteractorImpl {
    private val listHistory: MutableList<Track> = mutableListOf()
    fun addTrack(track: Track): List<Track> {

        listHistory.remove(track)
        if (listHistory.isEmpty()) {
            listHistory.add(track)
        } else {

            if (listHistory.size < MAX_HISTORY_LIST_SIZE) {
                listHistory.add(0, track)
            } else {
                (listHistory).removeLast()
                listHistory.add(0, track)
            }
        }
        return emptyList()
    }

    fun readTrackListFromDb() {
        listHistory.clear()
        listHistory.addAll(readTracksList())
    }

    fun writeTrackListToDb() {
        writeTrackList(listHistory)
    }

    fun getTrackList(): List<Track> {
        return listHistory
    }

    fun clearTracksList() {
        listHistory.clear()
    }

    private fun readTracksList(): List<Track> {

        val dto = getSavedPref(name = SAVED_TRACKS, key = SAVED_LIST, context = context)

        if (dto.isNullOrEmpty()) {
            return emptyList()
        } else {
            return dto.map {
                Track(
                    it.trackName,
                    it.artistName,
                    it.trackTimeMillis,
                    it.artworkUrl100,
                    it.trackId,
                    it.collectionName,
                    it.releaseDate,
                    it.primaryGenreName,
                    it.country,
                    it.previewUrl,
                )
            }
        }
    }

    private fun writeTrackList(list: MutableList<Track>) {
        val tracks = list as List<Track>
        setSavedPref(
            name = SAVED_TRACKS,
            key = SAVED_LIST,
            objects = tracks as Serializable,
            context = context
        )
    }

    companion object {
        const val SAVED_TRACKS = "SAVED_TRACKS"
        const val SAVED_LIST = "SAVED_LIST"
        const val MAX_HISTORY_LIST_SIZE = 10
    }
}
