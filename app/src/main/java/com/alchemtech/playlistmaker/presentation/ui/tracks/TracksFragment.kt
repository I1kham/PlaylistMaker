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
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.FragmentSearchBinding
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.ui.tracks.model.TracksFragmentModel
import com.alchemtech.playlistmaker.presentation.ui.tracks.model.TracksState
import com.alchemtech.playlistmaker.util.debounce
import org.koin.androidx.viewmodel.ext.android.viewModel

class TracksFragment : Fragment() {
    private val viewModel: TracksFragmentModel by viewModel()
    private var _binding: FragmentSearchBinding? = null
    private lateinit var inputEditText: EditText
    private lateinit var trackRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var noDataLinearLayout: LinearLayout
    private lateinit var noConnectionLinearLayout: LinearLayout
    private lateinit var clearButton: ImageView
    private lateinit var upDateBut: TextView
    private lateinit var searchHistoryTitle: TextView
    private lateinit var clearHistoryBut: TextView
    private lateinit var trackAdapter: TrackSearchAdapter
    private lateinit var onItemClickToTrackCardDebounce : (Track) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return _binding!!.root
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

        onItemClickToTrackCardDebounce = debounce<Track>(
            delayMillis = CLICK_DEBOUNCE_DELAY,
            coroutineScope = viewLifecycleOwner.lifecycleScope,
            useLastParam = true
        ) { track ->
            findNavController().navigate(R.id.action_tracksFragment_to_playerActivity)
            viewModel.clickOnTrack(track)
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
        _binding = null
    }

    private fun prepareHistoryTitle() {
        searchHistoryTitle = _binding!!.searchHistoryTitle
    }

    private fun prepareUpdateBut() {
        upDateBut = _binding!!.updateButNoConnection
        upDateBut.setOnClickListener {
            viewModel.updateResponse()
        }
    }

    private fun prepareEditTextClearBut() {
        clearButton = _binding!!.clearIcon
        clearButton.setOnClickListener {
            viewModel.clearEditTextButLogic()
        }
    }

    private fun prepareNoConnectionErr() {
        noConnectionLinearLayout = _binding!!.noConnection
    }

    private fun prepareNoDataErr() {
        noDataLinearLayout = _binding!!.noData
    }

    private fun prepareProgressBar() {
        progressBar = _binding!!.progressBar
    }

    private fun prepareClearHistBut() {
        clearHistoryBut = _binding!!.clearHistoryBut
        clearHistoryBut.setOnClickListener {
            viewModel.clearButSearchHistory()
        }
    }


    private fun prepareTrackRecyclerView() {
        trackRecyclerView = _binding!!.trackCardsRecyclerView
        trackRecyclerView.layoutManager =
            LinearLayoutManager(
                /* context = */ requireContext(),
                /* orientation = */ LinearLayoutManager.VERTICAL,
                /* reverseLayout = */ false
            )
    }

    private fun prepareInputedText() {
        inputEditText = _binding!!.inputTextForSearching
        inputEditText.addTextChangedListener(viewModel.textWatcher)
        inputEditText.doOnTextChanged { text, _, _, _ ->
            clearButton.isVisible = !text.isNullOrEmpty()
        }
        viewModel.inputEditTextListener(inputEditText)
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
                hideKeyBoard()
            }

            is TracksState.Content -> {
                state.tracks.upDateAdapter()
                showHistoryListButTitle(false)
                trackRecyclerView.visibility = View.VISIBLE
                showNoConnection(false)
                showNoDataErr(false)
                showProgressBar(false)
                hideKeyBoard()
            }

            is TracksState.Error -> {
                hideKeyBoard()
                showProgressBar(false)
                showHistoryListButTitle(
                    false
                )
                if (state.errorCode == -1) {
                    showNoConnection(true)
                } else {
                    showNoDataErr(true)
                }
            }

            is TracksState.History -> {
                hideKeyBoard()
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
        trackAdapter = TrackSearchAdapter(this)
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

    private fun hideKeyBoard() {
        val inputMethodManager =
            requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)
    }

    private companion object {
        const val CLICK_DEBOUNCE_DELAY = 300L
    }
}