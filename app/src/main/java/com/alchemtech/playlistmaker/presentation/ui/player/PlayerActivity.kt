package com.alchemtech.playlistmaker.presentation.ui.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.creators.PlayerDataFillingCreator
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.presentation.ui.player.model.PlayerState
import com.alchemtech.playlistmaker.presentation.ui.player.model.PlayerViewModel

/*Player*/
class PlayerActivity : AppCompatActivity() {

    private lateinit var viewModel: PlayerViewModel
    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareBinding()
        prepareViewModel()
        prepareBackBut()
        fillStrData()
    }

    private fun prepareBackBut() {
        binding.playerPreview.setOnClickListener {
            viewModel.backBut()
        }
    }

    private fun fillStrData() {
        viewModel.fill()
    }


    private fun prepareBinding() {
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun prepareViewModel() {
        viewModel = ViewModelProvider(
            this,
            PlayerViewModel.getViewModelFactory(
               // track
            )
        )[PlayerViewModel::class.java]
        viewModel.observeState().observe(this) {
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
            is PlayerState.FillViewWithTrackData -> {

            }
            is PlayerState.OnPrepared-> {
                PlayerDataFillingCreator.provide(binding, state.track)
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

            PlayerState.BackBut -> {
                finish()
            }
        }
    }

    private fun playBut() {
        binding.playBut.setOnClickListener {
            viewModel.playBut()
        }
    }
}