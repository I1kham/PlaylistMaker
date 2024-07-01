package com.alchemtech.playlistmaker.presentation.ui.tracks.model

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alchemtech.playlistmaker.creators.ListTrackRepositoryCreator
import com.alchemtech.playlistmaker.creators.MoveToActivityCreator
import com.alchemtech.playlistmaker.creators.SearchCreator
import com.alchemtech.playlistmaker.domain.api.TracksInteractor
import com.alchemtech.playlistmaker.domain.entity.Track

class TracksActivityViewModel(
) : ViewModel() {

    companion object {

        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()

        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                TracksActivityViewModel()
            }
        }
    }
    // TODO: сюда функции
    private val historyInteractor = ListTrackRepositoryCreator.provideListTrackDb()
    private val searchInteractor = SearchCreator.provideTracksInteractor()
    private val moveToActivity = MoveToActivityCreator.provideMoveToActivity()
    private val searchRunnable = Runnable { searchTrack(searchText) }
    private var searchText: String = ""
    private val tracksList = mutableListOf<Track>()
    private val handler = Handler(Looper.getMainLooper())
    private val stateLiveData = MutableLiveData<TracksActivityState>()
    private val tracksConsumer = object : TracksInteractor.TracksConsumer {
        override fun consume(foundedTracks: List<Track>?, errorCode: Int?) {
            if (foundedTracks.isNullOrEmpty()) {
                if (errorCode == -1) {
                    renderState(TracksActivityState.Error(-1))
                } else {
                    renderState(TracksActivityState.Error(-2))
                }
            } else {
                tracksList.clear()
                tracksList.addAll(foundedTracks)
                renderState(TracksActivityState.Content(tracksList))
            }
        }
    }

    internal fun backButLogic() {
        renderState(TracksActivityState.Exit)
    }

    internal fun clearEditTextButLogic() {
        renderState(TracksActivityState.InputTextClear(historyInteractor.getTrackList()))
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
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                onTextChangedLogic(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                afterTextChangedLogic(s)
            }
        }
    }

    private fun onTextChangedLogic(s: String) {
        searchText = s
        renderState(TracksActivityState.TextClearBut(s.isNotEmpty()))
        searchDebounce()
    }

    internal fun startModelLogic() {
        renderState(TracksActivityState.History(historyInteractor.getTrackList()))
    }

    internal fun clickOnTrack(track: Track) {
        addTrackToHistoryList(track)
        moveToActivity.toPlayer(track)
    }

    internal fun updateResponse() {
        searchTrack(searchText)
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
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY
        handler.postAtTime(
            searchRunnable,
            SEARCH_REQUEST_TOKEN,
            postTime,
        )
    }

    fun observeState(): LiveData<TracksActivityState> = stateLiveData


    private fun addTrackToHistoryList(track: Track) {
        historyInteractor.addTrack(track)
        renderState(TracksActivityState.History(historyInteractor.getTrackList()))
    }

    private fun renderState(state: TracksActivityState) {
        stateLiveData.postValue(state)
        stateLiveData.value
    }

    override fun onCleared() {
        super.onCleared()
       handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        historyInteractor.writeTrackList()
    }
}