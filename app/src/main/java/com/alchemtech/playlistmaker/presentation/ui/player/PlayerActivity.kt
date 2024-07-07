package com.alchemtech.playlistmaker.presentation.ui.player

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.ui.UiCalculator
import com.alchemtech.playlistmaker.presentation.ui.player.model.PlayerState
import com.alchemtech.playlistmaker.presentation.ui.player.model.PlayerViewModel
import org.koin.android.ext.android.inject


class PlayerActivity : AppCompatActivity(), UiCalculator, PlayerActivityFilling {

    private val viewModel: PlayerViewModel by inject()
    private lateinit var binding: ActivityPlayerBinding

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareBinding()
        observeRenderState()
        prepareBackBut()
        prepareViewModel()
    }

    private fun prepareViewModel() {
        viewModel.observeCurrentPosition().observe(this) {
            binding.playTime.text = it
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.onStop()
    }

    private fun prepareBackBut() {
        binding.playerPreview.setOnClickListener {
            finish()
        }
    }

    private fun prepareBinding() {
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun observeRenderState() {
        val a = viewModel.observeRenderState()
        a.observe(this) {
            render(it)
        }
    }

    private fun render(state: PlayerState) {
        when (state) {

            is PlayerState.Pause -> {
                binding.playBut.setImageResource(R.drawable.play_but)
            }

            is PlayerState.Play -> {
                binding.playBut.setImageResource(R.drawable.pause_but)
                fillWithBut(state.track)
            }

            is PlayerState.OnPrepared -> {
                fillWithBut(state.track)
            }

            is PlayerState.OnCompletion -> {
                binding.playTime.text = "00:00"
                fillWithBut(state.track)
                binding.playBut.setImageResource(R.drawable.play_but)
            }

            is PlayerState.Fill -> fill(state.track)
        }
    }

    private fun fillWithBut(track: Track) {
        fill(track)
        binding.playBut.isEnabled = true
        playBut()
    }

    private fun playBut() {
        binding.playBut.setOnClickListener {
            viewModel.playBut()
        }
        binding.playBut.isEnabled = true
    }

    private fun fill(track: Track) {
        fillPlayerActivity(track, binding, this)
    }
}