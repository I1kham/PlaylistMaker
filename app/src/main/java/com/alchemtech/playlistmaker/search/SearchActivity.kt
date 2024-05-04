package com.alchemtech.playlistmaker.search

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.PlayerActivity
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.track.Track
import com.alchemtech.playlistmaker.track.TrackApiService
import com.alchemtech.playlistmaker.track.TrackSearchAdapter
import com.alchemtech.playlistmaker.track.TracksResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private var historyList = mutableListOf<Track>()
    private val tracksList = arrayListOf<Track>()


    private val onItemClickToHistoryTrackCard = { track: Track ->
        // trackCardClicking(track)
        trackCardToPlayer(track)
    }

    private val onItemClickToTrackCard = { track: Track ->
        trackCardClicking(track)
        trackCardToPlayer(track)
    }

    private val searchRunnable = Runnable { searchTrack() }

    @SuppressLint("NotifyDataSetChanged")
    private fun trackCardClicking(track: Track) {
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


    private fun trackCardToPlayer(track: Track) {
        val trackCardClickIntent = Intent(this@SearchActivity, PlayerActivity::class.java).apply {
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


    private fun showTrackList() {
        val trackAdapter = TrackSearchAdapter(tracksList)
        val recyclerView = findViewById<RecyclerView>(R.id.trackCardsRecyclerView)
        onItemClickToTrackCard.also { trackAdapter.onItemClick = it }
        recyclerView.adapter = trackAdapter
        disableHistoryList()

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
                searchDebounce()
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
            allErrLayoutsGONE()
            inputEditText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    // ВЫПОЛНЯЙТЕ ПОИСКОВЫЙ ЗАПРОС ЗДЕСЬ
                    searchTrack()
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
            allErrLayoutsGONE()
            findViewById<RecyclerView>(R.id.trackCardsRecyclerView).adapter?.notifyDataSetChanged()
        }
    }

    private fun noDataErrLayoutVISIBLE() {
        val noDataLinearLayout = findViewById<LinearLayout>(R.id.noData)
        noDataLinearLayout.visibility = View.VISIBLE
        val noConnectionLinearLayout = findViewById<LinearLayout>(R.id.noConnection)
        noConnectionLinearLayout.visibility = View.GONE
    }

    private fun noConnectionErrLayoutVISIBLE() {
        val noDataLinearLayout = findViewById<LinearLayout>(R.id.noData)
        noDataLinearLayout.visibility = View.GONE
        val noConnectionLinearLayout = findViewById<LinearLayout>(R.id.noConnection)
        noConnectionLinearLayout.visibility = View.VISIBLE
    }

    private fun allErrLayoutsGONE() {
        val noDataLinearLayout = findViewById<LinearLayout>(R.id.noData)
        noDataLinearLayout.visibility = View.GONE
        val noConnectionLinearLayout = findViewById<LinearLayout>(R.id.noConnection)
        noConnectionLinearLayout.visibility = View.GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun searchTrack( ) {
        val inputEditText = findViewById<EditText>(R.id.inputTextForSearching)
        val text =  inputEditText.text.toString()
        val searchingBaseUrl = "https://itunes.apple.com"
        val retrofit = Retrofit.Builder()
            .baseUrl(searchingBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        tracksList.clear()
        retrofit.create(TrackApiService::class.java).search(text)
            .enqueue(object : Callback<TracksResponse> {
                override fun onResponse(
                    call: Call<TracksResponse>,
                    response: Response<TracksResponse>,
                ) {
                    if (response.isSuccessful) {

                        if (response.body()?.results?.isNotEmpty() == true) {
                            tracksList.addAll(response.body()?.results!!)
                            findViewById<RecyclerView>(R.id.trackCardsRecyclerView).adapter?.notifyDataSetChanged()
                        }
                        if (tracksList.isEmpty()) {
                            noDataErrLayoutVISIBLE()
                        }
                    } else {
                        noConnectionErrLayoutVISIBLE()
                    }
                }

                override fun onFailure(call: Call<TracksResponse>, t: Throwable) {
                    noConnectionErrLayoutVISIBLE()

                }
            })

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
            showTrackList()
        } else {
            enableHistoryList()
        }
    }

    private fun searchDebounce() {
        val handler = Handler(Looper.getMainLooper())
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }


    private companion object {
        const val TEXT_TO_SAVE = ""
        const val SEARCH_DEBOUNCE_DELAY = 2000L

    }
}






