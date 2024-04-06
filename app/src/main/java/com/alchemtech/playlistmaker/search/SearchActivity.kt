package com.alchemtech.playlistmaker.search

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
    //
    private val tracksListOf = ArrayList<Track>()

    private val searchingBaseUrl = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(searchingBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val searchService = retrofit.create(TrackApiService::class.java)
    private val trackAdapter = TrackSearchAdapter(tracksListOf)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)

        val recyclerView = findViewById<RecyclerView>(R.id.trackCardsRecyclerView)

        recyclerView.adapter = trackAdapter

        inputEditTextWorking()

        upDateSearchWorking()

        backButWork()

        clearButWorking()

        textWatcher()
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

            tracksListOf.clear()
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
        tracksListOf.clear()
        trackAdapter.notifyDataSetChanged()
        searchService.search(text)
            .enqueue(object : Callback<TracksResponse> {
                override fun onResponse(
                    call: Call<TracksResponse>,
                    response: Response<TracksResponse>,
                ) {
                    if (response.isSuccessful) {

                        if (response.body()?.results?.isNotEmpty() == true) {
                            tracksListOf.addAll(response.body()?.results!!)
                            trackAdapter.notifyDataSetChanged()
                        }
                        if (tracksListOf.isEmpty()) {
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
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val inputEditText = findViewById<EditText>(R.id.inputEditText)
        inputEditText.setText(savedInstanceState.getString(TEXTTOSAVE))
    }
    // В Kotlin для создания константной переменной мы используем companion object.
// Ключ должен быть константным, чтобы мы точно знали, что он не изменится

    private companion object {
        const val TEXTTOSAVE = ""
    }
}






