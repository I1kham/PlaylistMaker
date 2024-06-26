package com.alchemtech.playlistmaker.presentation.ui.tracks

import android.os.Handler
import android.os.Looper
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.alchemtech.playlistmaker.creators.ListTrackRepositoryCreator
import com.alchemtech.playlistmaker.creators.SearchCreator
import com.alchemtech.playlistmaker.domain.api.TracksInteractor
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.ui.tracks.model.TracksActivityState
import moxy.MvpPresenter


class TracksSearchPresenter: MvpPresenter<TracksView>() {
    private var searchText: String = ""

    private val tracksInteractor: TracksInteractor =
        SearchCreator.provideTracksInteractor()


    private val history =
        ListTrackRepositoryCreator.provideListTrackDb()

    internal fun clickOnTrack(track: Track) {
        addTrackToHistoryList(track)
        navigateToPlayer(track)
        println(track)
    }

    internal val tracksList = ArrayList<Track>()

    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { searchTrack() }

    private val tracksConsumer = object : TracksInteractor.TracksConsumer {
        override fun consume(foundedTracks: List<Track>?, errorMessage: String?) {

            handler.post {
                println("6516516516516"+foundedTracks) // TODO:
                if (foundedTracks.isNullOrEmpty()) {
                    render(TracksActivityState.Content(tracksList))

                    //view.render(TracksActivityState.Error(errorMessage.toString()))
                } else {
                    tracksList.clear()
                    tracksList.addAll(foundedTracks)
                    render(TracksActivityState.Content(tracksList))
                }
            }
        }
    }

    private fun render(state: TracksActivityState) {
        viewState.render(state)
    }

    fun onCreate() {
        getHistory()
        enableHistoryList()
    }


    override fun onDestroy() {
        handler.removeCallbacks(searchRunnable)
    }

    fun onPause() {
        saveHistory()
    }

    private fun enableHistoryList() {
        val historyList = history.getTrackList()
        if (historyList.isNotEmpty()) {
            render(TracksActivityState.History(historyList))
        }
    }


    internal fun inputEditTextWorking(editText: EditText) {
        editText.setOnClickListener {
            render(TracksActivityState.Content(tracksList))
            editText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    searchTrack() // выполнение задач
                }
                true
            }
        }
    }

    private fun searchTrack() {
        if (searchText.isNotEmpty()) {
            render(TracksActivityState.Loading)
            tracksList.clear()
            tracksInteractor.searchTracksInteractor(
                expression = searchText,
                consumer = tracksConsumer
            )
        }
    }

    internal fun upDateButSearchWorking() {
        searchTrack()
    }

    internal fun clearButLogic() {
        render(TracksActivityState.InputText(""))
        tracksList.clear()
        render(TracksActivityState.History(history.getTrackList()))
    }

    internal fun clearButSearchHistory() {
        history.clearTracksList()
        render(TracksActivityState.Content(tracksList))
    }


    private fun searchDebounce()
    {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private fun saveHistory() {// TODO: B_L
        history.writeTrackList()
    }

    private fun getHistory() {// TODO: B_L
        history.readTrackList()

    }

    private fun addTrackToHistoryList(track: Track) { // TODO: B_L
        history.addTrack(track)
    }


    private fun navigateToPlayer(track: Track) { // TODO: to del
        viewState.navigateTRackToPlayer(track)
    }

    internal fun afterTextChangedLogic(text: CharSequence?) {
        if (text?.isNotEmpty() == false) {
            render(TracksActivityState.History(history.getTrackList()))
        }
    }

    internal fun onTextChangedLogic(s: String) {
        searchText = s
        render(TracksActivityState.TextClearBut(s.isNotEmpty()))
        searchDebounce()
    }

    internal fun focusLogic(editText: EditText) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && editText.text.isEmpty()) {
                render(TracksActivityState.History(history.getTrackList()))
            }
        }
    }

    internal fun backButLogic() {
        viewState.stopView()
    }


    private companion object {
        const val SEARCH_DEBOUNCE_DELAY = 2000L // TODO: to controller
    }
}