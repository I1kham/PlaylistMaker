package com.alchemtech.playlistmaker.presentation.ui.tracks.model

import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alchemtech.playlistmaker.domain.api.SingleTrackInteractor
import com.alchemtech.playlistmaker.domain.api.TrackHistoryInteractor
import com.alchemtech.playlistmaker.domain.api.TracksInteractor
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.util.debounce
import kotlinx.coroutines.launch

class TracksFragmentModel(
    private val historyInteractor: TrackHistoryInteractor,
    private val searchInteractor: TracksInteractor,
    private val singleTrackInteractor: SingleTrackInteractor,
) : ViewModel() {
    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private var searchText: String = ""
    private var oldSearchText: String = ""
    private val tracksList = mutableListOf<Track>()
    private val stateLiveData = MutableLiveData<TracksState>()

    init {
        startModelLogic()
    }

    internal fun clearEditTextButLogic() {
        renderState(TracksState.InputTextClear(historyInteractor.getTrackList()))
        tracksList.clear()
    }

    internal fun clearButSearchHistory() {
        historyInteractor.clearTracksList()
        renderState(TracksState.Content(tracksList))
    }

    internal fun inputEditTextListener(editText: EditText) {
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchDebounce()
            }
            true
        }
    }

    internal val textWatcher by lazy {
        object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(s: Editable?) {
                afterTextChangedLogic(s)
            }
        }
    }

    private fun startModelLogic() {
        renderState(TracksState.History(historyInteractor.getTrackList()))
    }

    internal fun clickOnTrack(track: Track) {
        addTrackToHistoryList(track)
        singleTrackInteractor.writeTrack(track)
    }

    internal fun updateResponse() {
        searchTrack()
    }

    private fun afterTextChangedLogic(text: CharSequence?) {
        if (text?.isNotEmpty() == true) {
            searchText = text.toString()
            searchDebounce()
        } else {
            renderState(TracksState.History(historyInteractor.getTrackList()))
        }
    }

    private fun searchTrack() {
        if (oldSearchText != searchText) {
            renderState(TracksState.Loading)
            tracksList.clear()
            viewModelScope.launch {
                if (searchText.isNotEmpty()) {
                    searchInteractor
                        .searchTracks(searchText)
                        .collect { pair ->
                            processResult(pair.first, pair.second)
                        }
                }
            }
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

    private fun searchDebounce() {
        run(debounce<TracksFragmentModel>(
            delayMillis = SEARCH_DEBOUNCE_DELAY,
            coroutineScope = viewModelScope,
            useLastParam = true
        ) {
            searchTrack()
        })
    }

    fun observeState(): LiveData<TracksState> = stateLiveData

    private fun addTrackToHistoryList(track: Track) {
        historyInteractor.addTrack(track)
    }

    private fun renderState(state: TracksState) {
        stateLiveData.postValue(state)
        stateLiveData.value
    }

    internal fun save() {
        historyInteractor.writeTrackList()
    }
}