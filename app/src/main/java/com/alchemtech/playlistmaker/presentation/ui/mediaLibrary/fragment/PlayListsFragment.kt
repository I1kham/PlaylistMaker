package com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.App.Companion.PLAY_LIST_TRANSFER_KEY
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.FragmentPlayListsBinding
import com.alchemtech.playlistmaker.domain.entity.PlayList
import com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.model.PlayListsViewModel
import com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.state.PlayListsState
import com.alchemtech.playlistmaker.presentation.ui.playLikstCard.PlayListCardAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayListsFragment : Fragment() {

    private var binding: FragmentPlayListsBinding? = null
    private val viewModel: PlayListsViewModel by viewModel()
    private var progressBar: ProgressBar? = null
    private var recyclerView: RecyclerView? = null
    private var noDataLayout: ConstraintLayout? = null
    private var adapter: PlayListCardAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPlayListsBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareProgressBar()
        prepareAddPlayListButton()
        prepareRecyclerView()
        observeRenderState()
        prepareNoDataLayout() }

    override fun onDetach() {
        super.onDetach()
        binding = null
    }

    private fun prepareNoDataLayout() {
        noDataLayout = binding?.noDataLay
    }

    private fun prepareProgressBar() {
        progressBar = binding?.progressBar
    }

    private fun prepareAddPlayListButton() {
        binding?.addPlayListBut?.setOnClickListener {
            findNavController().navigate(R.id.action_mediaLibFragment_to_addPlayListFragment)
        }
    }

    private fun observeRenderState() {
        viewModel.observeState().observe(getViewLifecycleOwner()) {
            render(it)
        }
    }

    private fun prepareRecyclerView() {
        recyclerView = binding?.playListRecyclerView
        recyclerView?.layoutManager = GridLayoutManager(
            view?.context,
            2
        )
    }

    private fun render(state: PlayListsState) {
        when (state) {
            is PlayListsState.ShowList ->
                renderList(state.playLists)

            is PlayListsState.EmptyList ->
                renderEmptyState(true)

            is PlayListsState.Loading ->
                renderEmptyState(false)
        }
    }

    private fun renderEmptyState(isVisible: Boolean) {
        progressBar?.isVisible = !isVisible
        noDataLayout?.isVisible = isVisible
    }

    private fun renderList(playLists: List<PlayList>) {
        progressBar?.visibility = View.GONE
        noDataLayout?.visibility = View.GONE
        adapter = PlayListCardAdapter(playLists)
        adapter?.onItemClick = {playList :PlayList ->
            val bundle = bundleOf(PLAY_LIST_TRANSFER_KEY to playList.id  )
            findNavController().navigate(R.id.action_mediaLibFragment_to_playList, bundle)}
        recyclerView?.adapter = adapter

    }
}