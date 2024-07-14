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
import com.alchemtech.playlistmaker.presentation.ui.player.model.PlayerState
import com.alchemtech.playlistmaker.presentation.ui.player.model.PlayerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlayerFragment : Fragment(), UiCalculator, PlayerStringsFilling {

    private val viewModel: PlayerViewModel by viewModel()
    private var _binding: ActivityPlayerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ActivityPlayerBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeRenderState()
        prepareBackBut()
        prepareViewModel()
    }
    override fun onDetach() {
        super.onDetach()
        _binding = null
    }

    private fun prepareViewModel() {
        viewModel.observeCurrentPosition().observe(getViewLifecycleOwner()) {
            _binding?.playTime?.text = it
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
    }

    private fun prepareBackBut() {
        _binding!!.playerPreview.setOnClickListener {
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
                _binding?.playBut?.setImageResource(R.drawable.play_but)
            }

            is PlayerState.Play -> {
                _binding?.playBut?.setImageResource(R.drawable.pause_but)
                fillWithBut(state.track)
            }

            is PlayerState.OnPrepared -> {
                fillWithBut(state.track)
            }

            is PlayerState.OnCompletion -> {
                _binding?.playTime?.text = "00:00"
                fillWithBut(state.track)
                _binding?.playBut?.setImageResource(R.drawable.play_but)
            }

            is PlayerState.Fill -> fill(state.track)
        }
    }

    private fun fillWithBut(track: Track) {
        fill(track)
        playBut()
    }

    private fun playBut() {
        _binding?.playBut?.setOnClickListener {
            viewModel.playBut()
        }
        _binding?.playBut?.isEnabled = true
    }

    private fun fill(track: Track) {
        fillPlayerActivity(track, _binding!!, requireContext())
    }
}