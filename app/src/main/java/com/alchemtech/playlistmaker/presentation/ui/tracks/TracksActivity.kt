package com.alchemtech.playlistmaker.presentation.ui.tracks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.creators.TracksSearchPresenterCreator
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.ui.TrackUtils.convertToString
import com.alchemtech.playlistmaker.presentation.ui.player.PlayerActivity
import com.alchemtech.playlistmaker.presentation.ui.tracks.model.TracksActivityState
import moxy.MvpActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class TracksActivity : MvpActivity(), TracksView {

    private val onItemClickToTrackCard = { track: Track ->
        if (clickDebounce()) {
            tracksSearchPresenter.clickOnTrack(track)
        }
    }

    @InjectPresenter
    lateinit var tracksSearchPresenter: TracksSearchPresenter
    private var isClickAllowed: Boolean = true
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var inputEditText: EditText
    private lateinit var trackRecyclerView: RecyclerView

    private lateinit var progressBar: ProgressBar
    private lateinit var noDataLinearLayout: LinearLayout
    private lateinit var noConnectionLinearLayout: LinearLayout
    private lateinit var clearButton: ImageView
    private lateinit var upDateBut: Button
    private lateinit var searchHistoryTitle: TextView
    private lateinit var clearHistoryBut: TextView
    private lateinit var trackAdapter: TrackSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)
        inputEditText = findViewById(R.id.inputTextForSearching)

        trackRecyclerView = findViewById(R.id.trackCardsRecyclerView)

        progressBar = findViewById<ProgressBar>(R.id.progressBar)

        noDataLinearLayout = findViewById<LinearLayout>(R.id.noData)
        noConnectionLinearLayout = findViewById<LinearLayout>(R.id.noConnection)

        clearButton = findViewById<ImageView>(R.id.clearIcon)
        upDateBut = findViewById<Button>(R.id.updateButNoConnection)
        searchHistoryTitle = findViewById<TextView>(R.id.searchHistoryTitle)
        clearHistoryBut = findViewById<TextView>(R.id.clearHistoryBut)
        trackRecyclerView.layoutManager =
            LinearLayoutManager(
                /* context = */ this,
                /* orientation = */ LinearLayoutManager.VERTICAL,
                /* reverseLayout = */ false
            )

        tracksSearchPresenter =
            trackSearchPresenter()
        tracksSearchPresenter.onCreate()
        tracksSearchPresenter.focusLogic(inputEditText)

        upDateBut.setOnClickListener {
            tracksSearchPresenter.upDateButSearchWorking()
        }

        clearButton.setOnClickListener {
            tracksSearchPresenter.clearButLogic()
        }

        clearHistoryBut.setOnClickListener {
            tracksSearchPresenter.clearButSearchHistory()
        }

        backButWork()
        tracksSearchPresenter.inputEditTextWorking(inputEditText)

        inputEditText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                tracksSearchPresenter.onTextChangedLogic(s.toString())

            }

            override fun afterTextChanged(s: Editable?) {
                tracksSearchPresenter.afterTextChangedLogic(s)
            }
        })
    }
    @ProvidePresenter
    fun trackSearchPresenter(): TracksSearchPresenter {
return TracksSearchPresenterCreator.provideTracksSearchPresenter()
    }


    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }


    private fun backButWork() {
        val back = findViewById<Button>(R.id.pageSearchPreview)
        back.setOnClickListener {
            tracksSearchPresenter.backButLogic()
        }
    }

    override fun onPause() {
        super.onPause()
        tracksSearchPresenter.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        tracksSearchPresenter.onDestroy()

    }


    private fun showHistoryListButTitle(visibility: Boolean) {
        if (visibility) {
            clearHistoryBut.visibility = View.VISIBLE
            trackRecyclerView.visibility = View.VISIBLE
            searchHistoryTitle.visibility = View.VISIBLE
        } else {
            clearHistoryBut.visibility = View.GONE
            trackRecyclerView.visibility = View.GONE
            searchHistoryTitle.visibility = View.GONE
        }
    }

    private fun showNoConnection(visibility: Boolean) {
        if (visibility) {
            noConnectionLinearLayout.visibility = View.VISIBLE
        } else {
            noConnectionLinearLayout.visibility = View.GONE
        }
    }

    private fun showNoDataErr(visibility: Boolean) {
        noDataLinearLayout.isVisible = visibility
    }

    private fun showProgressBar(visibility: Boolean) {
        if (visibility) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun hideKeyBoard() {
        val inputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)
    }


    override fun stopView() {
        finish()
    }


    override fun navigateTRackToPlayer(track: Track) {

        val trackCardClickIntent =
            Intent(this, PlayerActivity::class.java).apply {
                putExtra(
                    "track",
                    track.convertToString()
                )
            }
        startActivity(trackCardClickIntent)
    }

    override fun render(state: TracksActivityState) {
        println("8484848484848484848484848")
        when (state) {
            is TracksActivityState.Loading -> {
                showProgressBar(true)
                showNoConnection(false)
                showNoDataErr(false)
                showHistoryListButTitle(false)
                hideKeyBoard()
            }

            is TracksActivityState.Content -> {
                state.tracks.upDateAdapter()

                showHistoryListButTitle(false)
                trackRecyclerView.visibility = View.VISIBLE
                showNoConnection(false)
                showNoDataErr(false)
                showProgressBar(false)
                hideKeyBoard()
            } //TODO()
            is TracksActivityState.Error -> {
                hideKeyBoard()
                showHistoryListButTitle(
                    false
                )
                if (state.errorMsg == " no connection") {
                    showNoConnection(true)
                } else {
                    showNoDataErr(true)
                }
            }// TODO()
            is TracksActivityState.History -> {
                hideKeyBoard()
                if (state.tracks.isNotEmpty()) {
                    state.tracks.upDateAdapter()
                    showHistoryListButTitle(true)
                }
                showProgressBar(false)
                showNoConnection(false)
                showNoDataErr(false)
            }

            is TracksActivityState.TextClearBut -> {
                clearButton.isVisible = state.visibility
            }

            is TracksActivityState.InputText -> {
                inputEditText.setText(state.text)
            }
        }
    }

    private fun List<Track>.upDateAdapter() {
        trackAdapter = TrackSearchAdapter(this)
        onItemClickToTrackCard.also { trackAdapter.onItemClick = it }
        trackRecyclerView.adapter = trackAdapter
    }


    private companion object {
        const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}