package com.alchemtech.playlistmaker.presentation.ui.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.creators.PlayerDataFillingCreator
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.presenters.PlayerFilling
import com.alchemtech.playlistmaker.presentation.ui.player.model.PlayerState
import com.alchemtech.playlistmaker.presentation.ui.player.model.PlayerViewModel

class PlayerActivity : AppCompatActivity() {

    private lateinit var viewModel: PlayerViewModel
    private lateinit var binding: ActivityPlayerBinding

    private lateinit var filler : PlayerFilling

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareBinding()
        prepareViewModel()
        observeRenderState()
        prepareBackBut()
        filler = PlayerDataFillingCreator.provide(binding)
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

    private fun prepareViewModel() {
        viewModel = ViewModelProvider(
            this,
            PlayerViewModel.getViewModelFactory(
            )
        )[PlayerViewModel::class.java]


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
            }

            is PlayerState.OnPrepared -> {
                fill( state.track)
                binding.playBut.isEnabled = true
                playBut()
            }

            PlayerState.OnCompletion -> {
                binding.playTime.text = "00:00"
                binding.playBut.setImageResource(R.drawable.play_but)
            }

            is PlayerState.SetPlayTime -> {
                binding.playTime.text = state.position
            }

            is PlayerState.Fill -> {
               fill( state.track)
            }
        }
    }

    private fun fill(track: Track){
        filler.fill(track)
    }

    private fun playBut() {
        binding.playBut.setOnClickListener {
            viewModel.playBut()
        }
        binding.playBut.isEnabled = true
    }
}