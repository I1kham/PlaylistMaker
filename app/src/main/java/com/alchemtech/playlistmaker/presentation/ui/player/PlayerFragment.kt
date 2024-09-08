package com.alchemtech.playlistmaker.presentation.ui.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.ui.UiCalculator
import com.alchemtech.playlistmaker.presentation.ui.main.StartActivity
import com.alchemtech.playlistmaker.presentation.ui.player.model.PlayerState
import com.alchemtech.playlistmaker.presentation.ui.player.model.PlayerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlayerFragment : Fragment(), UiCalculator, PlayerStringsFilling {

    private val viewModel: PlayerViewModel by viewModel()
    private var binding: ActivityPlayerBinding? = null

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
        (activity as StartActivity).bottomNavigationVisibility(false)
    }

    override fun onDetach() {
        super.onDetach()
        binding = null
    }

    private fun prepareViewModel() {
        viewModel.observeCurrentPosition().observe(getViewLifecycleOwner()) {
            binding?.playTime?.text = it
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
        (activity as StartActivity).bottomNavigationVisibility(true)
    }

    private fun prepareBackBut() {
        binding?.playerPreview?.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun observeRenderState() {
        val a = viewModel.observeRenderState()
        a.observe(getViewLifecycleOwner()) {
            render(it)
        }
    }

    private fun render(state: PlayerState) {
        when (state) {

            is PlayerState.Pause -> {
                binding?.playBut?.setImageResource(R.drawable.play_but)
            }

            is PlayerState.Play -> {
                binding?.playBut?.setImageResource(R.drawable.pause_but)
                playBut()
            }

            is PlayerState.OnPrepared -> {
                playBut()
            }

            is PlayerState.OnCompletion -> {
                binding?.playTime?.text = "00:00"
                playBut()
                binding?.playBut?.setImageResource(R.drawable.play_but)
            }

            is PlayerState.Fill ->  fill(state.track)

            is PlayerState.likeBut ->
                renderLikeBut(state.isFavorite)
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

    private fun fill(track: Track) {
        fillPlayerActivity(track, binding, requireContext())
        renderLikeBut(track.isFavorite)
    }
}