package com.alchemtech.playlistmaker.presentation.ui.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.App.Companion.PLAY_TRACK_TRANSFER_KEY
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.FragmentAddTrackToPlayListBinding
import com.alchemtech.playlistmaker.domain.entity.PlayList
import com.alchemtech.playlistmaker.presentation.ui.main.StartActivity
import com.alchemtech.playlistmaker.presentation.ui.playLikstBottomCard.PlayListBottomCardAdapter
import com.alchemtech.playlistmaker.presentation.ui.player.model.AddTrackToPlayListFragmentState
import com.alchemtech.playlistmaker.presentation.ui.player.model.AddTrackToPlayListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddTrackToPlayListFragment : Fragment() {
    private val viewModel: AddTrackToPlayListViewModel by viewModel()
    private var binding: FragmentAddTrackToPlayListBinding? = null
    private var trackId: String? = null
    private var progressBar: ProgressBar? = null
    private var noDataLayout: ConstraintLayout? = null
    private var recyclerView: RecyclerView? = null
    private lateinit var onItemClick: (PlayList) -> Unit

    private var adapter: PlayListBottomCardAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAddTrackToPlayListBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareAddNewPlayListButton()
        prepareProgressBar()
        prepareNoDataLayout()
        prepareOnItemClick()
        prepareRecyclerView()
        getTrackId()
        setTrackIdToVieModel()
        observeRenderState()
    }

    private fun setTrackIdToVieModel() {
        viewModel.setTrackID(trackId)
    }

    private fun getTrackId() {
        trackId = parentFragment?.parentFragment?.arguments?.getString(
            PLAY_TRACK_TRANSFER_KEY
        )
    }

    private fun prepareAddNewPlayListButton() {
        binding?.addPlayListBut?.setOnClickListener {
            parentFragment?.parentFragment?.findNavController()
                ?.navigate(R.id.action_playerActivity_to_addPlayListFragment2)
        }
    }

    private fun prepareProgressBar() {
        progressBar = binding?.progressBar
    }

    private fun render(state: AddTrackToPlayListFragmentState) {
        when (state) {
            AddTrackToPlayListFragmentState.Empty -> {
                false.progressBarVisibility()
                true.noDataVisibility()
            }

            is AddTrackToPlayListFragmentState.Loading -> {
                state.isLoading.progressBarVisibility()
                false.noDataVisibility()
            }

            is AddTrackToPlayListFragmentState.ShowList -> {
                false.progressBarVisibility()
                false.noDataVisibility()
                state.tracks.renderList()
            }

            is AddTrackToPlayListFragmentState.TrackAdded -> {
                if (state.added) {
                    showBottomMessage(getString(R.string.addedToPlayList, state.namePlayList))
                } else {
                    showBottomMessage(getString(R.string.doNotAddtoPlayList, state.namePlayList))
                }
            }

        }
    }

    private fun observeRenderState() {
        viewModel.observeRenderState().observe(getViewLifecycleOwner()) {
            render(it)
        }
    }

    private fun Boolean.progressBarVisibility() {
        progressBar?.isVisible = this
    }

    private fun Boolean.noDataVisibility() {
        noDataLayout?.isVisible = this
    }

    private fun prepareNoDataLayout() {
        noDataLayout = binding?.noDataLay
    }

    private fun List<PlayList>.renderList() {
        adapter = PlayListBottomCardAdapter(this)
        onItemClick.also {
            adapter?.onItemClick = it
        }
        recyclerView?.adapter = adapter
    }

    private fun prepareOnItemClick() {
        onItemClick = { playList: PlayList ->
            viewModel.addTrackTo(playList)
        }
    }

    private fun prepareRecyclerView() {
        recyclerView = binding?.playListRecyclerView
        recyclerView?.layoutManager = GridLayoutManager(
            view?.context,
            1
        )
    }

    private fun showBottomMessage(message: String) {
        (activity as StartActivity).bottomSheetShowMessage(message)
    }
}