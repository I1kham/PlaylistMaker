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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.creators.TracksSearchPresenterCreator
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.ui.TrackUtils.convertToString
import com.alchemtech.playlistmaker.presentation.ui.player.PlayerActivity


class TracksActivity : AppCompatActivity(), TracksView {

    private val onItemClickToTrackCard = { track: Track ->
        if (clickDebounce()) {
           tracksSearchPresenter.clickOnTrack(track)
        }
    }


    private lateinit var tracksSearchPresenter: TracksSearchPresenter
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

    private fun navigateToPlayer(track: Track) {

        val trackCardClickIntent =
            Intent(this@TracksActivity, PlayerActivity::class.java).apply {
                putExtra(
                    "track",
                    track.convertToString()
                )
            }
        startActivity(trackCardClickIntent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)

        tracksSearchPresenter =
            TracksSearchPresenterCreator.provideTracksSearchPresenter(this)
        tracksSearchPresenter.onCreate()
//        tracksSearchPresenter.somfing =
//            backButWork()

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

        val trackAdapter = TrackSearchAdapter(tracksSearchPresenter.tracksList)
        onItemClickToTrackCard.also { trackAdapter.onItemClick = it }

        trackRecyclerView.adapter = trackAdapter
        // trackRecyclerView.visibility= View.VISIBLE
        //trackRecyclerView.setOnClickListener(onItemClickToTrackCard)


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
                clearButVisibility(s)
                // tracksSearchPresenter.textChangeLogic(s)
                //  tracksSearchPresenter.focusLogic(inputEditText)
                tracksSearchPresenter.searchDebounce()

            }

            override fun afterTextChanged(s: Editable?) {
                // tracksSearchPresenter.focusLogic(inputEditText)
                //  tracksSearchPresenter.textChangeLogic(s)
                tracksSearchPresenter.setSearchText(s.toString())
            }

        })
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


    override fun showHistoryListButTitle(visibility: Boolean) {
        if (visibility) {
            clearHistoryBut.visibility = View.VISIBLE
            searchHistoryTitle.visibility = View.VISIBLE
        } else {
            clearHistoryBut.visibility = View.GONE
            searchHistoryTitle.visibility = View.GONE
        }
    }

    override fun showNoConnection(visibility: Boolean) {
        if (visibility) {
            noConnectionLinearLayout.visibility = View.VISIBLE
        } else {
            noConnectionLinearLayout.visibility = View.GONE
        }
    }

    override fun showNoDataErr(visibility: Boolean) {
        noDataLinearLayout.isVisible = visibility
    }

    override fun showProgressBar(visibility: Boolean) {
        if (visibility) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun hideKeyBoard() {
        val inputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)
    }

    override fun upDateRecycle() {
        trackRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun clearInputText() {
        inputEditText.setText("")
    }

    override fun stopView() {
        finish()
    }

    override fun showTrackRecycle(visibility: Boolean) {
        if (visibility) {
            trackRecyclerView.visibility = View.VISIBLE
        } else {
            trackRecyclerView.visibility = View.GONE
        }
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


    private fun clearButVisibility(s: CharSequence?) {
        clearButton.isVisible = !(s.isNullOrEmpty())
    }

    private companion object {
        const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}