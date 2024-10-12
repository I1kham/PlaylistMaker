package com.alchemtech.playlistmaker.presentation.ui.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alchemtech.playlistmaker.App.Companion.PLAY_TRACK_TRANSFER_KEY
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.ui.dpToPx
import com.alchemtech.playlistmaker.presentation.ui.main.StartActivity
import com.alchemtech.playlistmaker.presentation.ui.player.model.PlayerState
import com.alchemtech.playlistmaker.presentation.ui.player.model.PlayerViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlayerFragment : Fragment(), PlayerStringsFilling {

    private val viewModel: PlayerViewModel by viewModel()
    private var binding: ActivityPlayerBinding? = null
    private var bottomSheet: LinearLayout? = null
    private var addBut: ImageView? = null
    private var overlay: View? = null
    private var trackId: String? = null
    private val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            overlay?.isVisible = (newState != BottomSheetBehavior.STATE_HIDDEN)
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            // Do something for slide offset.
        }
    }

    companion object {
        private const val BOTTOM_FRAGMENT_SIZE = 505f
    }

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
        getTrackId()
        observeRenderState()
        prepareBackBut()
        prepareViewModel()
        likeButPrepare()
        addTopPlayListPrepare()
        prepareBottomSheet()
        prepareOverlay()
        false.bottomNavigatorVisibility()
    }

    private fun getTrackId() {
        trackId = arguments?.getString(PLAY_TRACK_TRANSFER_KEY)
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

    private fun prepareOverlay() {
        overlay = binding?.overlay
    }

    private fun prepareBottomSheet() {
        binding?.let {
            bottomSheet = it.standardBottomSheet
            BottomSheetBehavior.from(bottomSheet!!).addBottomSheetCallback(bottomSheetCallback)
        }
    }

    private fun addTopPlayListPrepare() {
        addBut = binding?.playerAddToListBut
        addBut?.isEnabled = true
        addBut?.setOnClickListener {
            true.playerBottomSheetVisible()
        }
    }

    private fun prepareViewModel() {
        viewModel.observeCurrentPosition().observe(getViewLifecycleOwner()) {
            binding?.playTime?.text = it
        }
        viewModel.prepareModel(trackId)
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
                binding?.playerProgressBar?.isVisible = false
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

            is PlayerState.LikeBut -> {
                renderLikeBut(state.track.isFavorite)
            }

            is PlayerState.Preparing -> binding?.playerProgressBar?.isVisible = state.prepare
            PlayerState.Error -> showBottomMessage(getString(R.string.no_internet_bottom_message))
        }
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

    private fun Boolean.playerBottomSheetVisible() {
        BottomSheetBehavior.from(bottomSheet!!).maxHeight =
            dpToPx(BOTTOM_FRAGMENT_SIZE, requireContext())
        bottomSheet?.isVisible = this
        bottomSheet?.let {
            if (this) {
                BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_HIDDEN
            }
        }
    }

    private fun showBottomMessage(message: String) {
        (activity as StartActivity).bottomSheetShowMessage(message)
    }
}