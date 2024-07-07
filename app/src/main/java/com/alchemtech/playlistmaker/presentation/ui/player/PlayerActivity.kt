package com.alchemtech.playlistmaker.presentation.ui.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.creators.PlayerDataFillingCreator
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.presenters.PlayerFilling
import com.alchemtech.playlistmaker.presentation.ui.player.model.PlayerState
import com.alchemtech.playlistmaker.presentation.ui.player.model.PlayerViewModel
import org.koin.android.ext.android.inject

class PlayerActivity : AppCompatActivity() {

    private val viewModel: PlayerViewModel by inject()
    private lateinit var binding: ActivityPlayerBinding

    private lateinit var filler: PlayerFilling   //todo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareBinding()
        observeRenderState()
        prepareBackBut()
        viewModel.observeCurrentPosition().observe(this) {
            binding.playTime.text = it
        }
        filler = PlayerDataFillingCreator.provide(binding, applicationContext)
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
                fillWithBut(state.track)
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

            is PlayerState.fill -> fill(state.track)
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

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        viewModel.onWindowFocusChanged(hasFocus)
    }

    private fun fill(track: Track) {
        filler.fill(track)
    }
}