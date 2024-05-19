package com.alchemtech.playlistmaker.presentation.tracks

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.domain.SearchCreator
import com.alchemtech.playlistmaker.domain.TracksResponseContainer
import com.alchemtech.playlistmaker.domain.api.TracksInteractor
import com.alchemtech.playlistmaker.domain.models.Track
import com.alchemtech.playlistmaker.presentation.MAX_HISTORY_LIST_SIZE
import com.alchemtech.playlistmaker.presentation.SAVED_TRACKS
import com.alchemtech.playlistmaker.presentation.SearchHistory
import com.alchemtech.playlistmaker.presentation.player.PlayerActivity


class TracksActivity : AppCompatActivity() {

    private var historyList = mutableListOf<Track>()
    private val tracksList = mutableListOf<Track>()

    private val onItemClickToHistoryTrackCard = { track: Track ->
        if (clickDebounce()) {
            navigateToPlayer(track)
        }
    }

    private val onItemClickToTrackCard = { track: Track ->
        if (clickDebounce()) {
            clickOnTrack(track)
            navigateToPlayer(track)
        }
    }

    private val searchRunnable = Runnable { searchTrack() }

    private var isClickAllowed = true

    private val handler = Handler(Looper.getMainLooper())


    @SuppressLint("NotifyDataSetChanged")
    private fun clickOnTrack(track: Track) {


        historyList.remove(track)
        if (historyList.isEmpty()) {
            historyList.add(track)
        } else {
            if (historyList.size < MAX_HISTORY_LIST_SIZE) {
                historyList.add(0, track)

            } else {
                historyList.removeLast()
                historyList.add(0, track)
            }
        }
        findViewById<RecyclerView>(R.id.trackCardsRecyclerView).adapter?.notifyDataSetChanged()
    }


    private fun navigateToPlayer(track: Track) {
        val trackCardClickIntent =
            Intent(this@TracksActivity, PlayerActivity::class.java).apply {
                putExtra(
                    "track",
                    track
                )
            }
        startActivity(trackCardClickIntent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getHistory()
        setContentView(R.layout.activity_search)

        inputEditTextWorking()

        upDateSearchWorking()

        backButWork()

        clearButWorking()

        textWatcher()

        clearButSearchHistory()

        enableHistoryList()

    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    private fun enableHistoryList() {

        if (historyList.isNotEmpty()) {
            findViewById<TextView>(R.id.clearHistoryBut).visibility = View.VISIBLE
            findViewById<TextView>(R.id.searchHistoryTitle).visibility = View.VISIBLE

            val trackAdapter = TrackSearchAdapter(historyList)
            val recyclerView = findViewById<RecyclerView>(R.id.trackCardsRecyclerView)
            onItemClickToHistoryTrackCard.also { trackAdapter.onItemClick = it }
            recyclerView.adapter = trackAdapter
        } else disableHistoryList()

    }

    private fun disableHistoryList() {
        findViewById<TextView>(R.id.clearHistoryBut).visibility = View.GONE
        findViewById<TextView>(R.id.searchHistoryTitle).visibility = View.GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun enableTrackList() {
        runOnUiThread {
            val trackAdapter = TrackSearchAdapter(tracksList)
            val recyclerView = findViewById<RecyclerView>(R.id.trackCardsRecyclerView)
            onItemClickToTrackCard.also { trackAdapter.onItemClick = it }
            recyclerView.adapter = trackAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun clearButSearchHistory() {

        findViewById<TextView>(R.id.clearHistoryBut).setOnClickListener {
            findViewById<TextView>(R.id.searchHistoryTitle).visibility = View.GONE
            findViewById<TextView>(R.id.clearHistoryBut).visibility = View.GONE

            historyList.clear()
            findViewById<RecyclerView>(R.id.trackCardsRecyclerView).adapter?.notifyDataSetChanged()

        }

    }

    private fun textWatcher() {
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButVisibility(s)
                textChangeLogic(s)
                focusLogic()
                //    searchDebounce() todo
            }

            override fun afterTextChanged(s: Editable?) {
                focusLogic()
                textChangeLogic(s)
            }
        }
        val inputEditText = findViewById<EditText>(R.id.inputTextForSearching)
        inputEditText.addTextChangedListener(simpleTextWatcher)
    }

    private fun clearButVisibility(s: CharSequence?) {
        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        clearButton.isVisible = !(s.isNullOrEmpty())
    }

    private fun inputEditTextWorking() {
        val inputEditText = findViewById<EditText>(R.id.inputTextForSearching)
        inputEditText.setOnClickListener {
            setAllErrLayoutsGONE()
            inputEditText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    searchTrack() // выполнение задач

                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)
                }
                true
            }
        }
    }

    private fun upDateSearchWorking() {
        val upDateBut = findViewById<Button>(R.id.updateButNoConnection)
        upDateBut.setOnClickListener {
            searchTrack()
        }
    }

    private fun backButWork() {
        val back = findViewById<Button>(R.id.pageSearchPreview)
        back.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun clearButWorking() {
        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        clearButton.setOnClickListener {
            val inputEditText = findViewById<EditText>(R.id.inputTextForSearching)
            inputEditText.setText("")

            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)

            tracksList.clear()
            setAllErrLayoutsGONE()
            findViewById<RecyclerView>(R.id.trackCardsRecyclerView).adapter?.notifyDataSetChanged()
        }
    }

    private fun setNoDataErrLayoutVisible() {
        runOnUiThread {
            val noDataLinearLayout = findViewById<LinearLayout>(R.id.noData)
            noDataLinearLayout.visibility = View.VISIBLE
            val noConnectionLinearLayout = findViewById<LinearLayout>(R.id.noConnection)
            noConnectionLinearLayout.visibility = View.GONE
        }
    }

    private fun setNoConnectionErrLayoutVisible() {
        runOnUiThread {
            val noDataLinearLayout = findViewById<LinearLayout>(R.id.noData)
            noDataLinearLayout.visibility = View.GONE
            val noConnectionLinearLayout = findViewById<LinearLayout>(R.id.noConnection)
            noConnectionLinearLayout.visibility = View.VISIBLE
        }
    }

    private fun setAllErrLayoutsGONE() {
        runOnUiThread {
            val noDataLinearLayout = findViewById<LinearLayout>(R.id.noData)
            noDataLinearLayout.visibility = View.GONE
            val noConnectionLinearLayout = findViewById<LinearLayout>(R.id.noConnection)
            noConnectionLinearLayout.visibility = View.GONE
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun searchTrack() {
        if (hasInternetConnection(this)) {


            val inputEditText = findViewById<EditText>(R.id.inputTextForSearching)
            val text = inputEditText.text

            if (!text.isNullOrEmpty()) {

                setProgressBarVisible()
                tracksList.clear()
                val tracksInteractor = SearchCreator.provideTracksInteractor()
                val tracksConsumer = object : TracksInteractor.TracksConsumer {
                    override fun consume(foundTracks: TracksResponseContainer) {
                        if (foundTracks.tracksList.isEmpty()) {
                            setNoDataErrLayoutVisible()
                        } else {
                            tracksList.addAll(foundTracks.tracksList)
                            enableTrackList()
                        }
                        setProgressBarGone()
                    }
                }

                tracksInteractor.searchTracksInteractor(
                    expression = text.toString(),
                    consumer = tracksConsumer
                )
            }
        } else {
            setNoConnectionErrLayoutVisible()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val inputText = findViewById<TextView>(R.id.inputTextForSearching).text
        outState.putString(/* key = */ TEXT_TO_SAVE, /* value = */ inputText.toString())
        saveHistory()
    }

    override fun onPause() {
        super.onPause()
        saveHistory()
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val inputEditText = findViewById<EditText>(R.id.inputTextForSearching)
        inputEditText.setText(savedInstanceState.getString(TEXT_TO_SAVE))
        getHistory()

    }

    private fun saveHistory() {
        SearchHistory().setHistoryListToSharePreferences(
            getSharedPreferences(
                SAVED_TRACKS,
                MODE_PRIVATE
            ), this.historyList
        )
    }

    private fun getHistory() {
        historyList = SearchHistory().getHistoryListFromSharePreferences(
            getSharedPreferences(
                SAVED_TRACKS,
                MODE_PRIVATE
            )
        )
    }

    private fun focusLogic() {
        findViewById<TextView>(R.id.inputTextForSearching).setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && findViewById<TextView>(R.id.inputTextForSearching).text.isEmpty()) {
                disableHistoryList()
            }
        }
    }

    private fun textChangeLogic(text: CharSequence?) {
        if (text?.isNotEmpty() == true) {
            enableTrackList()
            disableHistoryList()
        } else {
            enableHistoryList()
        }
    }

    private fun searchDebounce() {
        val handler = Handler(Looper.getMainLooper())
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private fun setProgressBarVisible() {
        setAllErrLayoutsGONE()
        runOnUiThread {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
    }
}
    private fun setProgressBarGone() {
        runOnUiThread {
            val progressBar = findViewById<ProgressBar>(R.id.progressBar)
            progressBar.visibility = View.GONE
        }
    }


    private companion object {
        const val TEXT_TO_SAVE = ""
        const val SEARCH_DEBOUNCE_DELAY = 2000L
        const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}

fun hasInternetConnection(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    var wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
    if (wifiInfo != null && wifiInfo.isConnected) {
        return true
    }
    wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
    if (wifiInfo != null && wifiInfo.isConnected) {
        return true
    }
    wifiInfo = cm.activeNetworkInfo
    return wifiInfo != null && wifiInfo.isConnected
}