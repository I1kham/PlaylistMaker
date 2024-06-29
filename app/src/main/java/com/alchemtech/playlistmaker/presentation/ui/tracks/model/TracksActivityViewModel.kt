package com.alchemtech.playlistmaker.presentation.ui.tracks.model

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alchemtech.playlistmaker.creators.ListTrackRepositoryCreator
import com.alchemtech.playlistmaker.creators.SearchCreator
import com.alchemtech.playlistmaker.domain.api.TracksInteractor
import com.alchemtech.playlistmaker.domain.entity.Track

class TracksActivityViewModel(
    private val application: Application,
) : AndroidViewModel(application) {

    companion object {

        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()

        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                TracksActivityViewModel(
                    this[APPLICATION_KEY] as Application
                )
            }
        }
    }


    // TODO: сюда функции
    val historyInteractor = ListTrackRepositoryCreator.provideListTrackDb()
    val searchInteractor = SearchCreator.provideTracksInteractor()

    private val searchRunnable = Runnable { searchTrack(searchText) }

    private var searchText: String = ""
    private var latestSearchText: String? = null

    private val tracksList = mutableListOf<Track>()

    private val handler = Handler(Looper.getMainLooper())

    private val stateLiveData = MutableLiveData<TracksActivityState>()

    private val tracksConsumer = object : TracksInteractor.TracksConsumer {
        override fun consume(foundedTracks: List<Track>?, errorMessage: String?) {

            if (foundedTracks.isNullOrEmpty()) {
                renderState(TracksActivityState.Content(tracksList))
            } else {
                tracksList.clear()
                tracksList.addAll(foundedTracks)
                renderState(TracksActivityState.Content(tracksList))
            }
        }
    }


    internal fun clearEditTextButLogic() {
        renderState(TracksActivityState.InputText(""))
        tracksList.clear()
    }

    internal fun clearButSearchHistory() {
        historyInteractor.clearTracksList()
        renderState(TracksActivityState.Content(tracksList))
    }

    internal fun inputEditTextListener(editText: EditText) {
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchTrack(searchText) // выполнение задач
            }
            true
        }
    }

    internal val textWatcher by lazy {
        object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
           // renderState(TracksActivityState.TextClearBut(s.isNullOrEmpty()))
        }

        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onTextChangedLogic(s.toString())
        }

        override fun afterTextChanged(s: Editable?) {
            afterTextChangedLogic(s)
           // renderState(TracksActivityState.TextClearBut(s.isNullOrEmpty()))
        }
    }
    }

    internal fun onTextChangedLogic(s: String) {
        searchText = s
        renderState(TracksActivityState.TextClearBut(s.isNotEmpty()))
        searchDebounce()
    }

    internal fun start() {

        renderState(TracksActivityState.History(historyInteractor.getTrackList()))
    }

    internal fun clickOnTrack(track: Track) {
        addTrackToHistoryList(track)
        navigateToPlayer(track)
        println(track) // TODO:
    }


    private fun afterTextChangedLogic(text: CharSequence?) {
        if (text?.isNotEmpty() == false) {
            renderState(TracksActivityState.History(historyInteractor.getTrackList()))
            renderState(TracksActivityState.TextClearBut(false))
        }
    }

    private fun searchTrack(s: String) {
        if (s.isNotEmpty()) {
            renderState(TracksActivityState.Loading)
            tracksList.clear()
            searchInteractor.searchTracksInteractor(
                expression = s,
                consumer = tracksConsumer
            )
        }
    }

    private fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    fun observeState(): LiveData<TracksActivityState> = stateLiveData


    private fun addTrackToHistoryList(track: Track) { // TODO: B_L
        historyInteractor.addTrack(track)
        renderState(TracksActivityState.History(historyInteractor.getTrackList()))
    }

    private fun navigateToPlayer(track: Track) { // TODO: to del
        renderState(TracksActivityState.NavigateTrackToPlayer(track))
    }

    private fun renderState(state: TracksActivityState) {
        stateLiveData.postValue(state)
    }

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        historyInteractor.writeTrackList()
    }
}