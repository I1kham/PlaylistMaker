package com.alchemtech.playlistmaker.domain.impl

import com.alchemtech.playlistmaker.domain.api.HistoryRepository
import com.alchemtech.playlistmaker.domain.api.SingleTrackInteractor
import com.alchemtech.playlistmaker.domain.entity.Track

class SingleTrackInteractorImpl(private val repository: HistoryRepository) :
    SingleTrackInteractor {

    init {
        repository.setNameKey(name = SAVED_TRACK, key = TRACK)
    }

    override fun writeTrack(track: Track) {
        writeTrackList(mutableListOf(track))
    }

    override fun readTrack() : Track {
     return readTracksList().first()
    }

    private fun readTracksList(): List<Track> {
        val dto = repository.getSavedPref()
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
        repository.setSavedPref(
            objects = tracks
        )
    }
    companion object {
        const val SAVED_TRACK = "SAVED_TRACK"
        const val TRACK = "TRACK"
    }
}
