package com.alchemtech.playlistmaker.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
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

    private var tracksList = mutableListOf<Track>()

    private val searchHistory = SearchHistory()

    private val searchingBaseUrl = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(searchingBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val searchService = retrofit.create(TrackApiService::class.java)
    private var trackAdapter = TrackSearchAdapter(tracksList)
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)

        val recyclerView = findViewById<RecyclerView>(R.id.trackCardsRecyclerView)

        recyclerView.adapter = trackAdapter

        trackAdapter.onItemClick =
            { track: Track ->
                searchHistory.addTrackToHistoryList(track)
                println("ffffffffffffffffffff")
            }

        inputEditTextWorking()

        upDateSearchWorking()

        backButWork()

        clearButWorking()

        textWatcher()

        clearButSearchHistory()

        if (historyList.size > 0) {
            findViewById<TextView>(R.id.searchHistoryTitle).visibility = View.VISIBLE
            findViewById<Button>(R.id.clearHistoryBut).visibility = View.VISIBLE
            //trackAdapter= TrackSearchAdapter(historyList)
            findViewById<RecyclerView>(R.id.trackCardsRecyclerView).adapter =
                TrackSearchAdapter(historyList.reversed())
            //trackAdapter.notifyDataSetChanged()
        }


    }

    private fun clearButSearchHistory() {

        findViewById<Button>(R.id.clearHistoryBut).setOnClickListener {
            findViewById<TextView>(R.id.searchHistoryTitle).visibility = View.GONE
            findViewById<Button>(R.id.clearHistoryBut).visibility = View.GONE
            historyList.clear()
            findViewById<RecyclerView>(R.id.trackCardsRecyclerView).adapter =
                TrackSearchAdapter(emptyList())
        }

    }

    private fun textWatcher() {
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val clearButton = findViewById<ImageView>(R.id.clearIcon)
                clearButton.isVisible = !(s.isNullOrEmpty())

            }

            override fun afterTextChanged(s: Editable?) {
                //empty
                //inputString = s.toString()
                //searchTrack(s.toString())
            }
        }
        val inputEditText = findViewById<EditText>(R.id.inputEditText)
        inputEditText.addTextChangedListener(simpleTextWatcher)
    }

    private fun inputEditTextWorking() {
        val inputEditText = findViewById<EditText>(R.id.inputEditText)
        inputEditText.setOnClickListener {
            allErrLayoutsGONE()
            inputEditText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    // ВЫПОЛНЯЙТЕ ПОИСКОВЫЙ ЗАПРОС ЗДЕСЬ
                    searchTrack(inputEditText.text.toString())
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
        val inputEditText = findViewById<EditText>(R.id.inputEditText)
        upDateBut.setOnClickListener {
            searchTrack(inputEditText.text.toString())
        }
    }

    private fun backButWork() {
        val back = findViewById<Button>(R.id.pageSearchPreview)
        back.setOnClickListener {
            finish()
        }
    }

    private fun clearButWorking() {
        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        clearButton.setOnClickListener {
            val inputEditText = findViewById<EditText>(R.id.inputEditText)
            inputEditText.setText("")

            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)

            tracksList.clear()
            trackAdapter.notifyDataSetChanged()
            allErrLayoutsGONE()
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
        val noDataLineraLayout = findViewById<LinearLayout>(R.id.noData)
        noDataLineraLayout.visibility = View.GONE
        val noConnectionLinearLayout = findViewById<LinearLayout>(R.id.noConnection)
        noConnectionLinearLayout.visibility = View.GONE
    }

    private fun searchTrack(text: String) {
        tracksList.clear()
        trackAdapter.notifyDataSetChanged()
        searchService.search(text)
            .enqueue(object : Callback<TracksResponse> {
                override fun onResponse(
                    call: Call<TracksResponse>,
                    response: Response<TracksResponse>,
                ) {
                    if (response.isSuccessful) {

                        if (response.body()?.results?.isNotEmpty() == true) {
                            tracksList.addAll(response.body()?.results!!)
                            trackAdapter.notifyDataSetChanged()
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
        val inputText = findViewById<TextView>(R.id.inputEditText).text
        outState.putString(/* key = */ TEXTTOSAVE, /* value = */ inputText.toString())

        searchHistory.setHistoryListToSharePreferences(
            getSharedPreferences(
                SAVED_TRACKS,
                MODE_PRIVATE
            ), historyList
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val inputEditText = findViewById<EditText>(R.id.inputEditText)
        inputEditText.setText(savedInstanceState.getString(TEXTTOSAVE))
        historyList = searchHistory.getHistoryListFromSharePreferences(
            getSharedPreferences(
                SAVED_TRACKS,
                MODE_PRIVATE
            )
        )

    }

    // В Kotlin для создания константной переменной мы используем companion object.
// Ключ должен быть константным, чтобы мы точно знали, что он не изменится

    private companion object {
        const val TEXTTOSAVE = ""

    }
}






