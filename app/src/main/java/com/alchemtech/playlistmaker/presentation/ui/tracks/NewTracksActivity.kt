package com.alchemtech.playlistmaker.presentation.ui.tracks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.databinding.ActivitySearchBinding
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.ui.TrackUtils.convertToString
import com.alchemtech.playlistmaker.presentation.ui.player.PlayerActivity
import com.alchemtech.playlistmaker.presentation.ui.tracks.model.TracksActivityState
import com.alchemtech.playlistmaker.presentation.ui.tracks.model.TracksActivityViewModel

class NewTracksActivity : ComponentActivity() {
    private var isClickAllowed: Boolean = true
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var viewModel: TracksActivityViewModel
    private lateinit var binding: ActivitySearchBinding

    private lateinit var inputEditText: EditText
    private lateinit var trackRecyclerView: RecyclerView
    private lateinit var backButton: Button
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
        binding = ActivitySearchBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            TracksActivityViewModel.getViewModelFactory()
        )[TracksActivityViewModel::class.java]


        viewModel.observeState().observe(this) {
            render(it)
        }


        inputEditText = binding.inputTextForSearching
        inputEditText.addTextChangedListener(viewModel.textWatcher)
        viewModel.inputEditTextListener(inputEditText)

        trackRecyclerView = binding.trackCardsRecyclerView

        backButton = binding.pageSearchPreview
        backButton.setOnClickListener {
            finish()
        }
        clearHistoryBut = binding.clearHistoryBut
        clearHistoryBut.setOnClickListener {
            viewModel.clearButSearchHistory()
        }

        progressBar = binding.progressBar

        noDataLinearLayout = binding.noData

        noConnectionLinearLayout = binding.noConnection

        clearButton = binding.clearIcon
        clearButton.setOnClickListener {
            viewModel.clearEditTextButLogic()
        }

        upDateBut = binding.updateButNoConnection

        searchHistoryTitle = binding.searchHistoryTitle

        trackRecyclerView.layoutManager =
            LinearLayoutManager(
                /* context = */ this,
                /* orientation = */ LinearLayoutManager.VERTICAL,
                /* reverseLayout = */ false
            )



        viewModel.start()




    }


    private fun render(state: TracksActivityState) {
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
                    //  state.tracks.upDateAdapter()
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
//                if(state.text.isEmpty()){
//
//                }
            }

            is TracksActivityState.NavigateTrackToPlayer -> {
                val trackCardClickIntent =
                    Intent(this, PlayerActivity::class.java).apply {
                        putExtra(
                            "track",
                            state.track.convertToString()
                        )
                    }
                startActivity(trackCardClickIntent)
            } //TODO()
        }
    }

    private fun List<Track>.upDateAdapter() {
        val onItemClickToTrackCard = { track: Track ->
            if (clickDebounce()) {
                viewModel.clickOnTrack(track)
            }
        }
        trackAdapter = TrackSearchAdapter(this)
        onItemClickToTrackCard.also { trackAdapter.onItemClick = it }
        trackRecyclerView.adapter = trackAdapter
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
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

    private companion object {
        const val CLICK_DEBOUNCE_DELAY = 1000L
    }

}