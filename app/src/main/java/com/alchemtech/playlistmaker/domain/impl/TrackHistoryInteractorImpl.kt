package com.alchemtech.playlistmaker.domain.impl

import com.alchemtech.playlistmaker.domain.api.HistoryRepository
import com.alchemtech.playlistmaker.domain.api.TrackHistoryInteractor
import com.alchemtech.playlistmaker.domain.entity.Track

class TrackHistoryInteractorImpl(private val historyRepository: HistoryRepository) :
    TrackHistoryInteractor {
    private val listHistory: MutableList<Track> = mutableListOf()

    init {
        readTrackList()
    }

    override fun addTrack(track: Track) {

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
    }

    override fun readTrackList() {
        listHistory.clear()
        listHistory.addAll(readTracksList())
    }

    override fun writeTrackList() {
        writeTrackList(listHistory)
    }

    override fun getTrackList(): List<Track> {
        return listHistory
    }

    override fun clearTracksList() {
        listHistory.clear()
    }

    private fun readTracksList(): List<Track> {

        val dto = historyRepository.getSavedTracks()

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
        historyRepository.setTracksToSave(
            tracksList = tracks
        )
    }

    companion object {
        const val MAX_HISTORY_LIST_SIZE = 10
    }
}
