package com.alchemtech.playlistmaker.presentation.ui.playList.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.App.Companion.PLAY_LIST_TRANSFER_KEY
import com.alchemtech.playlistmaker.App.Companion.PLAY_TRACK_TRANSFER_KEY
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.FragmentTracksRecyclerViewBinding
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.ui.main.StartActivity
import com.alchemtech.playlistmaker.presentation.ui.playList.PlayListFragment
import com.alchemtech.playlistmaker.presentation.ui.playList.fragments.model.TracksRecycleFragmentModel
import com.alchemtech.playlistmaker.presentation.ui.playList.fragments.state.TracksRecycleFragmentState
import com.alchemtech.playlistmaker.presentation.ui.track_card.TrackCardAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class TracksRecycleFragment : Fragment() {
    private val viewModel: TracksRecycleFragmentModel by viewModel()
    private var binding: FragmentTracksRecyclerViewBinding? = null
    private lateinit var trackAdapter: TrackCardAdapter
    private lateinit var onItemLongClick: (Track) -> Unit
    private var trackRecyclerView: RecyclerView? = null
    private var listId: Long? = null
    private val bottomSheetSize = 266f
    private lateinit var onItemClickToTrackCard: (Track) -> Unit


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
        false.bottomNavigatorVisibility()
        prepareOnItemClickToTrackCard()
        listId = parentFragment?.arguments?.getLong(PLAY_LIST_TRANSFER_KEY) ?: (
                parentFragment?.parentFragment?.arguments?.getLong(PLAY_LIST_TRANSFER_KEY)
                )

        prepareTrackRecyclerView()

        viewModel.observeRenderState().observe(getViewLifecycleOwner()) {
            render(it)
        }
        listId?.let {
            viewModel.getTracks(it)
        }
        bottomSheetTuning()

    }

    override fun onResume() {
        super.onResume()
        bottomSheetTuning()
    }

    private fun prepareOnItemClickToTrackCard() {
        onItemClickToTrackCard = { track ->
            val bundle = bundleOf(PLAY_TRACK_TRANSFER_KEY to track.trackId  )
            parentFragment?.parentFragment?.findNavController()?.navigate(R.id.action_playList_to_playerActivity, bundle)
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
        trackRecyclerView?.adapter = trackAdapter
        onItemClickToTrackCard.also { trackAdapter.onItemClick = it }
       // onItemLongClick.also { trackAdapter.onItemLongClick = it }
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

    private fun Boolean.bottomNavigatorVisibility() {
        (activity as StartActivity).bottomNavigationVisibility(this)
    }

    private fun bottomSheetTuning() {
        try {
            (parentFragment?.parentFragment as PlayListFragment).setBottomTuning(
                bottomSheetSize, false
            )
        } catch (e: Exception) {
            (parentFragment as PlayListFragment).setBottomTuning(bottomSheetSize, false)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    internal fun deletePlaylist(trackId : String) {
        MaterialAlertDialogBuilder(requireContext())
            .setBackground(resources.getDrawable((R.drawable.background)))
            .setTitle("Удалить трек")
            .setMessage(
               "Хотите удалить трек?"
            )
            .setNegativeButton(R.string.no) { _, _ ->
            }
            .setPositiveButton(R.string.yes) { _, _ ->
                    viewModel.deleteTrack(trackId)

            }
            .show()
    }


}