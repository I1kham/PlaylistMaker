package com.alchemtech.playlistmaker.domain.useCase

import android.content.Context
import com.alchemtech.playlistmaker.creators.HistoryRepositoryCreator
import com.alchemtech.playlistmaker.domain.entity.Track
import java.io.Serializable

class TrackHistoryUseCaseImpl(val context: Context) : TrackHistoryUseCase {

    private val listHistory: MutableList<Track> = mutableListOf()

  private  val historyRepository =
      HistoryRepositoryCreator.provideHistoryRepository(
          name = SAVED_TRACKS,
          key = SAVED_LIST,
          context = context
      )

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

    override fun readTrackListFromDb() {
        listHistory.clear()
        listHistory.addAll(readTracksList())
    }

    override fun writeTrackListToDb() {
        writeTrackList(listHistory)
    }

    override fun getTrackList(): List<Track> {
        return listHistory
    }

    override fun clearTracksList() {
        listHistory.clear()
    }

    private fun readTracksList(): List<Track> {

        val dto = historyRepository.getSavedPref()

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
        historyRepository.setSavedPref(
            objects = tracks as Serializable,
        )
    }

    companion object {
        const val SAVED_TRACKS = "SAVED_TRACKS"
        const val SAVED_LIST = "SAVED_LIST"
        const val MAX_HISTORY_LIST_SIZE = 10
    }

}
