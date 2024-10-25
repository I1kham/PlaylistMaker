package com.alchemtech.playlistmaker.presentation.ui.tracks.model

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alchemtech.playlistmaker.domain.api.TrackHistoryInteractor
import com.alchemtech.playlistmaker.domain.api.TracksInteractor
import com.alchemtech.playlistmaker.domain.db.TracksDbInteractor
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.util.debounce
import kotlinx.coroutines.launch

class TracksFragmentModel(
    private val historyInteractor: TrackHistoryInteractor,
    private val searchInteractor: TracksInteractor,
    private val tracksDb: TracksDbInteractor,
) : ViewModel() {
    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
    private var searchText: String? = null
    private var oldSearchText: String? = null
    private val tracksList = mutableListOf<Track>()
    private val stateLiveData = MutableLiveData<TracksState>()
    private val tracksSearchDebounce = debounce<String>(
        delayMillis = SEARCH_DEBOUNCE_DELAY,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { searchText ->
        searchTracks(searchText)
    }
    internal val textWatcher by lazy {
        object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchText = s.toString()
                if (s.isNullOrEmpty()) {
                    oldSearchText = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                afterTextChangedLogic(s)
            }
        }
    }

    init {
        startModelLogic()
    }

    fun observeState(): LiveData<TracksState> = stateLiveData

    internal fun clearEditTextButLogic() {
        renderState(TracksState.InputTextClear(historyInteractor.getTrackList()))
        tracksList.clear()
    }

    internal fun clearButSearchHistory() {
        historyInteractor.clearTracksList()
        renderState(TracksState.Content(tracksList))
    }


    private fun startModelLogic() {
        renderState(TracksState.History(historyInteractor.getTrackList()))
    }

    internal fun clickOnTrack(track: Track) {
        addTrackToHistoryList(track)
        viewModelScope.launch {
            tracksDb.addToTrackDb(track)
        }
    }

    internal fun updateResponse() {
        searchTracks(searchText!!)
    }

    private fun afterTextChangedLogic(searchText: CharSequence?) {
        searchDebounce(searchText.toString())
    }

    private fun searchTracks(searchText: String?) {
        if (!searchText.isNullOrEmpty()) {
            renderState(TracksState.Loading)
            tracksList.clear()
            viewModelScope.launch {
                searchInteractor
                    .searchTracks(searchText)
                    .collect { pair ->
                        processResult(pair.first, pair.second)
                    }
            }
        } else {
            renderState(TracksState.History(historyInteractor.getTrackList()))
        }
    }

    private fun processResult(foundTracks: List<Track>?, errorCode: Int?) {
        if (foundTracks.isNullOrEmpty()) {
            if (errorCode == -1) {
                renderState(TracksState.Error(-1))
            } else {
                renderState(TracksState.Error(-2))
            }
        } else {
            tracksList.clear()
            tracksList.addAll(foundTracks)
            renderState(TracksState.Content(tracksList))
            oldSearchText = searchText
        }
    }

    private fun searchDebounce(searchText: String?) {
        if (oldSearchText != searchText) {
            tracksSearchDebounce(searchText!!)
        }
    }

    private fun addTrackToHistoryList(track: Track) {
        historyInteractor.addTrack(track)
    }

    private fun renderState(state: TracksState) {
        stateLiveData.postValue(state)
    }

    internal fun save() {
        historyInteractor.writeTrackList()
    }
}