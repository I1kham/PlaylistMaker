package com.alchemtech.playlistmaker.presentation.ui.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.creators.PlayerDataFillingCreator
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.ui.TrackUtils.convertFromString
import com.alchemtech.playlistmaker.presentation.ui.player.model.PlayerActivityState
import com.alchemtech.playlistmaker.presentation.ui.player.model.PlayerActivityViewModel

/*Player*/
class PlayerrActivity : AppCompatActivity() {

    private lateinit var track: Track
    private lateinit var viewModel: PlayerActivityViewModel
    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareBinding()
        getTrackFromIntent()
        prepareViewModel()
        prepareBackBut()
        fillStrData()
    }

    override fun finish() {
        super.finish()
        viewModel.onDestroy()
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
            PlayerActivityViewModel.getViewModelFactory(track)
        )[PlayerActivityViewModel::class.java]
        viewModel.observeState().observe(this) {
            render(it)
        }
    }

    private fun render(state: PlayerActivityState) {
        when (state) {
            is PlayerActivityState.Pause -> {
                binding.playBut.setImageResource(R.drawable.play_but)
            }

            is PlayerActivityState.Play -> {
                binding.playBut.setImageResource(R.drawable.pause_but)
            }// TODO()
            is PlayerActivityState.FillViewWithTrackData -> {

            }//TODO()
            PlayerActivityState.OnPrepared -> {
                PlayerDataFillingCreator.provide(binding, track)
                binding.playBut.isEnabled = true
                playBut()
            }

            PlayerActivityState.OnCompletion -> {
                binding.playTime.text = "00:00"
                binding.playBut.setImageResource(R.drawable.play_but)
            }

            is PlayerActivityState.SetPlayTime -> {
                binding.playTime.text = state.position
            }

            PlayerActivityState.BackBut -> {
                finish()
            }
        }
    }

    private fun getTrackFromIntent() {
        track = convertFromString(intent.getSerializableExtra("track").toString())
    }

    private fun playBut() {
        binding.playBut.setOnClickListener {
            viewModel.playBut()
        }
    }
}