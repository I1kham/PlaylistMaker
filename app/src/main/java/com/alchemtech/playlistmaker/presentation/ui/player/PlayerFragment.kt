package com.alchemtech.playlistmaker.presentation.ui.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.domain.entity.PlayList
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.ui.main.StartActivity
import com.alchemtech.playlistmaker.presentation.ui.playLikstBottomCard.PlayListBottomCardAdapter
import com.alchemtech.playlistmaker.presentation.ui.player.model.PlayerState
import com.alchemtech.playlistmaker.presentation.ui.player.model.PlayerViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlayerFragment : Fragment(), PlayerStringsFilling {

    private val viewModel: PlayerViewModel by viewModel()
    private var binding: ActivityPlayerBinding? = null
    private var recyclerView: RecyclerView? = null
    private var noDataLayout: ConstraintLayout? = null
    private var bottomSheet: LinearLayout? = null
    private var addBut: ImageView? = null
    private var overlay: View? = null
    private lateinit var onItemClick: (PlayList) -> Unit
    private lateinit var adapter: PlayListBottomCardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = ActivityPlayerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeRenderState()
        prepareBackBut()
        prepareViewModel()
        likeButPrepare()
        addTopPlayListPrepare()
        prepareBottomSheet()
        bottomSheetVisible(false)
        prepareAddPlayListButton()
        prepareOverlay()
        false.bottomNavigatorVisibility()
        prepareRecyclerView()
        prepareNoDataLayout()
        prepareOnItemClick()
    }

    override fun onResume() {
        super.onResume()
        false.bottomNavigatorVisibility()
    }

    override fun onDetach() {
        super.onDetach()
        binding = null
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    override fun onStop() {
        super.onStop()
        true.bottomNavigatorVisibility()
    }

    private fun Boolean.bottomNavigatorVisibility() {
        (activity as StartActivity).bottomNavigationVisibility(this)
    }

    private fun prepareOnItemClick() {
        onItemClick = { playList: PlayList ->
            viewModel.addTrackTo(playList)
        }
    }

    private fun prepareOverlay() {
        overlay = binding?.overlay
    }

    private fun prepareAddPlayListButton() {
        binding?.addPlayListBut?.setOnClickListener {
            findNavController().navigate(R.id.action_playerActivity_to_addPlayListFragment2)
        }
    }

    private fun prepareBottomSheet() {
        binding?.let {
            bottomSheet = it.standardBottomSheet
            BottomSheetBehavior.from(bottomSheet!!).maxHeight = 1000
        }
    }

    private fun addTopPlayListPrepare() {
        addBut = binding?.playerAddToListBut
        addBut?.isEnabled = true
        addBut?.setOnClickListener {
            viewModel.addToPlaylist()
        }
    }


    private fun prepareViewModel() {
        viewModel.observeCurrentPosition().observe(getViewLifecycleOwner()) {
            binding?.playTime?.text = it
        }
    }


    private fun prepareBackBut() {
        binding?.playerPreview?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeRenderState() {
        viewModel.observeRenderState().observe(getViewLifecycleOwner()) {
            render(it)

        }
    }

    private fun render(state: PlayerState) {
        when (state) {

            is PlayerState.Pause -> {
                binding?.playBut?.setImageResource(R.drawable.play_but)
                fillFragment(state.track)
            }

            is PlayerState.Play -> {
                binding?.playBut?.setImageResource(R.drawable.pause_but)
                fillFragment(state.track)
            }

            is PlayerState.OnPrepared -> {
                fillFragment(state.track)
            }

            is PlayerState.OnCompletion -> {
                binding?.playTime?.text = "00:00"
                binding?.playBut?.setImageResource(R.drawable.play_but)
                fillFragment(state.track)
            }

            is PlayerState.Fill -> {
                fillAll(state.track)
                playBut()
            }

            is PlayerState.LikeBut ->
                renderLikeBut(state.track.isFavorite)

            is PlayerState.ShowList -> {
                binding?.progressBar?.visibility = View.GONE
                renderList(state.playLists)
                bottomSheetVisible(true)
            }

            PlayerState.EmptyList -> {
                binding?.progressBar?.visibility = View.GONE
                renderEmptyState()
                bottomSheetVisible(true)
            }

            is PlayerState.TrackAdded -> {
                binding?.progressBar?.visibility = View.GONE
                if (state.added) {

                    showBottomMessage(getString(R.string.addedToPlayList, state.namePlayList))
                } else {
                    showBottomMessage(getString(R.string.doNotAddtoPlayList, state.namePlayList))
                }
            }

            PlayerState.LoadingAdd ->
                binding?.progressBar?.visibility = View.VISIBLE
        }
    }

    private fun renderEmptyState() {
        noDataLayout?.visibility = View.VISIBLE
    }

    private fun renderList(playLists: List<PlayList>) {
        noDataLayout?.visibility = View.GONE
        adapter = PlayListBottomCardAdapter(playLists)
        onItemClick.also {
            adapter.onItemClick = it
        }
        recyclerView?.adapter = adapter
    }

    private fun fillFragment(track: Track) {
        binding?.let {
            if (it.releaseDateText.text == "Год") {
                fill(track)
                playBut()
            }
        }
    }

    private fun renderLikeBut(isLiked: Boolean?) {
        when (isLiked) {
            true -> binding?.playerTrackLike!!.setImageResource(R.drawable.isliked)
            else -> binding?.playerTrackLike!!.setImageResource(R.drawable.like)
        }
    }

    private fun playBut() {
        binding?.playBut?.setOnClickListener {
            viewModel.playBut()
        }
        binding?.playBut?.isEnabled = true
    }

    private fun likeButPrepare() {
        binding?.playerTrackLike?.setOnClickListener {
            viewModel.onFavoriteClicked()
        }
        binding?.playBut?.isEnabled = true
    }

    private fun fillAll(track: Track) {
        fillAll(track, binding, requireContext())
        renderLikeBut(track.isFavorite)
    }

    private fun fill(track: Track) {
        fill(track, binding, requireContext())
        renderLikeBut(track.isFavorite)
    }

    private fun prepareRecyclerView() {
        recyclerView = binding?.playListRecyclerView
        recyclerView?.layoutManager = GridLayoutManager(
            view?.context,
            1
        )
    }

    private fun prepareNoDataLayout() {
        noDataLayout = binding?.noDataLay
    }

    private fun bottomSheetVisible(isVisible: Boolean) {
        bottomSheet?.let {
            if (isVisible) {
                BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_HALF_EXPANDED
            } else {
                BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_HIDDEN
            }

        }
    }

    private fun showBottomMessage(message: String) {
        (activity as StartActivity).bottomSheetShowMessage(message)
    }
}