package com.alchemtech.playlistmaker.presentation.ui.playList.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.App.Companion.PLAY_LIST_TRANSFER_KEY
import com.alchemtech.playlistmaker.databinding.FragmentTracksRecyclerViewBinding
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.ui.playList.fragments.model.TracksRecycleFragmentModel
import com.alchemtech.playlistmaker.presentation.ui.playList.fragments.state.TracksRecycleFragmentState
import com.alchemtech.playlistmaker.presentation.ui.track_card.TrackCardAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class TracksRecycleFragment : Fragment() {
    private val viewModel: TracksRecycleFragmentModel by viewModel()
    private var binding: FragmentTracksRecyclerViewBinding? = null
    private lateinit var trackAdapter: TrackCardAdapter
    private lateinit var onItemLongClick: (Track) -> Unit
    private var trackRecyclerView: RecyclerView? = null
private var listId : Long? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTracksRecyclerViewBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("TracksRecycleFragment onViewCreated")

        listId = parentFragment?.parentFragment?.arguments?.getLong(PLAY_LIST_TRANSFER_KEY)
        println(listId)
        prepareTrackRecyclerView()

        viewModel.observeRenderState().observe(getViewLifecycleOwner()) {
            render(it)
        }
        listId?.let {
            viewModel.getTracks(it)
        }
    }

    private fun render(state: TracksRecycleFragmentState) {
        when (state) {
            is TracksRecycleFragmentState.Content -> {
                binding?.noData?.isVisible = false
                state.tracks.upDateAdapter()
            }

            TracksRecycleFragmentState.Empty -> binding?.noData?.isVisible = true
        }
    }

    private fun List<Track>.upDateAdapter() {
        trackAdapter = TrackCardAdapter(this)
        trackRecyclerView?.isVisible = true
        //  onItemLongClick.also { trackAdapter.onItemClick = it }
        trackRecyclerView?.adapter = trackAdapter
    }

    private fun prepareTrackRecyclerView() {
        trackRecyclerView = binding?.trackCardsRecyclerView
        trackRecyclerView?.layoutManager =
            LinearLayoutManager(
                /* context = */ requireContext(),
                /* orientation = */ LinearLayoutManager.VERTICAL,
                /* reverseLayout = */ false
            )
    }

}