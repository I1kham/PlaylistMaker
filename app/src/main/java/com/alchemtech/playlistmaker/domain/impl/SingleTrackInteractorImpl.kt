package com.alchemtech.playlistmaker.domain.impl

import com.alchemtech.playlistmaker.domain.api.HistoryRepository
import com.alchemtech.playlistmaker.domain.api.SingleTrackInteractor
import com.alchemtech.playlistmaker.domain.entity.Track

class SingleTrackInteractorImpl(private val historyRepository: HistoryRepository) :
    SingleTrackInteractor {

    override fun writeTrack(track: Track) {
        writeTrackList(mutableListOf(track))
    }

    override fun readTrack() : Track {
     return readTracksList().first()
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
            objects = tracks
        )
    }

}
