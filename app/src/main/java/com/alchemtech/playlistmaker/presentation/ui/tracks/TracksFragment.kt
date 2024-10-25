package com.alchemtech.playlistmaker.presentation.ui.tracks

import android.annotation.SuppressLint
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.App.Companion.PLAY_TRACK_TRANSFER_KEY
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.FragmentSearchBinding
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.ui.track_card.TrackCardAdapter
import com.alchemtech.playlistmaker.presentation.ui.tracks.model.TracksFragmentModel
import com.alchemtech.playlistmaker.presentation.ui.tracks.model.TracksState
import com.alchemtech.playlistmaker.util.debounce
import org.koin.androidx.viewmodel.ext.android.viewModel

class TracksFragment : Fragment() {
    private val viewModel: TracksFragmentModel by viewModel()
    private var binding: FragmentSearchBinding? = null
    private lateinit var inputEditText: EditText
    private lateinit var trackRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var noDataLinearLayout: LinearLayout
    private lateinit var noConnectionLinearLayout: LinearLayout
    private lateinit var clearButton: ImageView
    private lateinit var upDateBut: TextView
    private lateinit var searchHistoryTitle: TextView
    private lateinit var clearHistoryBut: TextView
    private lateinit var trackAdapter: TrackCardAdapter
    private lateinit var onItemClickToTrackCardDebounce: (Track) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViewModel()
        prepareInputedText()
        prepareTrackRecyclerView()
        prepareClearHistBut()
        prepareProgressBar()
        prepareNoDataErr()
        prepareNoConnectionErr()
        prepareEditTextClearBut()
        prepareUpdateBut()
        prepareHistoryTitle()
        prepareOnItemClickToTrackCardDebounce()
    }

    private fun prepareOnItemClickToTrackCardDebounce() {
        onItemClickToTrackCardDebounce = debounce<Track>(
            delayMillis = CLICK_DEBOUNCE_DELAY,
            coroutineScope = viewLifecycleOwner.lifecycleScope,
            useLastParam = true
        ) { track ->
            val bundle = bundleOf(PLAY_TRACK_TRANSFER_KEY to track.trackId  )
            viewModel.clickOnTrack(track)
            findNavController().navigate(R.id.action_tracksFragment_to_playerActivity, bundle)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onStart() {
        super.onStart()
        trackRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        viewModel.save()
    }

    override fun onDetach() {
        super.onDetach()
        binding = null
    }

    private fun prepareHistoryTitle() {
        binding?.let {
            searchHistoryTitle = it.searchHistoryTitle
        }
    }

    private fun prepareUpdateBut() {
        binding?.let {
            upDateBut = it.updateButNoConnection
            upDateBut.setOnClickListener {
                viewModel.updateResponse()
            }
        }
    }

    private fun prepareEditTextClearBut() {
        binding?.let {
            clearButton = it.clearIcon
            clearButton.setOnClickListener {
                viewModel.clearEditTextButLogic()
            }
        }
    }

    private fun prepareNoConnectionErr() {
        binding?.let {
            noConnectionLinearLayout = it.noConnection
        }
    }

    private fun prepareNoDataErr() {
        binding?.let {
            noDataLinearLayout = it.noData
        }
    }

    private fun prepareProgressBar() {
        binding?.let {
            progressBar = it.progressBar
        }
    }
        private fun prepareClearHistBut() {
            binding?.let {
                clearHistoryBut = it.clearHistoryBut
                clearHistoryBut.setOnClickListener {
                    viewModel.clearButSearchHistory()
                }
            }

        }

    private fun prepareTrackRecyclerView() {
        binding?.let {
            trackRecyclerView = it.trackCardsRecyclerView
            trackRecyclerView.layoutManager =
                LinearLayoutManager(
                    /* context = */ requireContext(),
                    /* orientation = */ LinearLayoutManager.VERTICAL,
                    /* reverseLayout = */ false
                )
        }
    }

    private fun prepareInputedText() {
        binding?.let {
        inputEditText = it.inputTextForSearching
        inputEditText.addTextChangedListener(viewModel.textWatcher)
        inputEditText.doOnTextChanged { text, _, _, _ ->
            clearButton.isVisible = !text.isNullOrEmpty()
        }
    }
    }

    private fun prepareViewModel() {
        viewModel.observeState().observe(getViewLifecycleOwner()) {
            render(it)
        }
    }

    private fun render(state: TracksState) {
        when (state) {
            is TracksState.Loading -> {
                showProgressBar(true)
                showNoConnection(false)
                showNoDataErr(false)
                showHistoryListButTitle(false)
                keyBoardVisibility(false)
            }

            is TracksState.Content -> {
                state.tracks.upDateAdapter()
                showHistoryListButTitle(false)
                trackRecyclerView.visibility = View.VISIBLE
                showNoConnection(false)
                showNoDataErr(false)
                showProgressBar(false)
                keyBoardVisibility(false)
            }

            is TracksState.Error -> {
                showProgressBar(false)
                showHistoryListButTitle(
                    false
                )
                if (state.errorCode == -1) {
                    showNoConnection(true)
                    keyBoardVisibility(false)
                } else {
                    showNoDataErr(true)
                    keyBoardVisibility(true)
                }
            }

            is TracksState.History -> {
                keyBoardVisibility(false)
                showHistoryList(state.tracks)
            }

            is TracksState.TextClearBut -> {
                clearButton.isVisible = state.visibility
            }

            is TracksState.InputTextClear -> {
                inputEditText.text = null
                showHistoryList(state.tracks)
            }
        }
    }

    private fun showHistoryList(tracks: List<Track>) {
        if (tracks.isNotEmpty()) {
            tracks.upDateAdapter()
            showHistoryListButTitle(true)
        }
        showProgressBar(false)
        showNoConnection(false)
        showNoDataErr(false)
    }

    private fun List<Track>.upDateAdapter() {
        trackAdapter = TrackCardAdapter(this)
        onItemClickToTrackCardDebounce.also { trackAdapter.onItemClick = it }
        trackRecyclerView.adapter = trackAdapter
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

    private fun keyBoardVisibility(visibility: Boolean) {
        val inputMethodManager =
            requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        when (visibility) {
            true -> inputMethodManager?.showSoftInput(inputEditText, 0)
            else -> inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)
        }
    }

    private companion object {
        const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}