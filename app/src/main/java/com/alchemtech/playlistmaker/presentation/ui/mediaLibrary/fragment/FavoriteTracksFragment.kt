package com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.FragmentFavoriteTracksBinding
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.model.FavoriteTracksViewModel
import com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.model.FavoriteTracksViewState
import com.alchemtech.playlistmaker.presentation.ui.trackCard.TrackCardAdapter
import com.alchemtech.playlistmaker.util.debounce
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteTracksFragment : Fragment() {

    private var _binding: FragmentFavoriteTracksBinding? = null
    private val viewModel: FavoriteTracksViewModel by viewModel()
    private lateinit var trackAdapter: TrackCardAdapter
    private lateinit var onItemClickToTrackCardDebounce: (Track) -> Unit
    private lateinit var trackRecyclerView: RecyclerView

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFavoriteTracksBinding.inflate(layoutInflater)
        return _binding?.root
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViewModel()
        prepareOnItemClickToTrackCardDebounce()
        prepareTrackRecyclerView()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onStart() {
        super.onStart()
        trackRecyclerView.adapter?.notifyDataSetChanged()
    }

    private fun prepareViewModel() {
        viewModel.observeState().observe(getViewLifecycleOwner()) {
            render(it)
        }
    }

    private fun render(state: FavoriteTracksViewState) {
        when (state) {
            is FavoriteTracksViewState.Empty -> {
                _binding!!.noDataLay.visibility = View.VISIBLE
                _binding!!.trackCardsRecyclerView.visibility = View.GONE
            }

            is FavoriteTracksViewState.TracksList -> {
                _binding!!.noDataLay.visibility = View.GONE
                _binding!!.trackCardsRecyclerView.visibility = View.VISIBLE
                state.tracks.upDateAdapter()
            }
        }
    }


    private fun List<Track>.upDateAdapter() {
        trackAdapter = TrackCardAdapter(this)
        onItemClickToTrackCardDebounce.also { trackAdapter.onItemClick = it }
        trackRecyclerView.adapter = trackAdapter
    }

    private fun prepareOnItemClickToTrackCardDebounce() {
        onItemClickToTrackCardDebounce = debounce<Track>(
            delayMillis = CLICK_DEBOUNCE_DELAY,
            coroutineScope = viewLifecycleOwner.lifecycleScope,
            useLastParam = true
        ) { track ->
            findNavController().navigate(R.id.action_mediaLibFragment_to_playerActivity)
            viewModel.clickOnTrack(track)
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

    private companion object {
        const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}