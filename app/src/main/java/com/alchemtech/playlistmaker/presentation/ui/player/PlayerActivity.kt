package com.alchemtech.playlistmaker.presentation.ui.player

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.creators.PlayerCreator
import com.alchemtech.playlistmaker.creators.PlayerDataFillingCreator
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.domain.api.PlayerRepository
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.domain.player.PlayerInteractor
import com.alchemtech.playlistmaker.presentation.ui.PlayerTimeFormatter
import com.alchemtech.playlistmaker.presentation.ui.TrackUtils.convertFromString

@Suppress("DEPRECATION")
open class PlayerActivity : AppCompatActivity() {
    private lateinit var track: Track
    private lateinit var binding: ActivityPlayerBinding
    private lateinit var player: PlayerInteractor

    private val currentPositionTask = createUpdateCurrentPositionTask()


    var mainThreadHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getTrackFromIntent()
        prepareBinding()
        preparePlayer()
        fillViewWithTrackData()
        backButWorking()
        playBut()
    }

    private fun fillViewWithTrackData() {
        PlayerDataFillingCreator.provide(binding, track)
    }

    private fun prepareBinding() {
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun getTrackFromIntent() {

        track = convertFromString(intent.getSerializableExtra("track").toString())

    }

    private fun preparePlayer() {
        PlayerCreator.providePlayer(track).also { player = it }


        val onPreparedListenerConsumer =
            PlayerRepository.OnPreparedListenerConsumer {
                binding.playBut.isEnabled = true
               // binding.playTime.text = PlayerTimeFormatter.format(player.duration())
            }

        val onCompletionListenerConsumer =
            PlayerRepository.OnCompletionListenerConsumer {
                binding.playTime.text = "00:00"
                binding.playBut.setImageResource(R.drawable.play_but)
            }


        val pauseConsumer = object : PlayerInteractor.PauseConsumer {
            override fun consume() {
                binding.playBut.setImageResource(R.drawable.play_but)

                killCurrentPositionTask()
            }
        }
        val startConsumer = object : PlayerInteractor.StartConsumer {
            override fun consume() {
                binding.playBut.setImageResource(R.drawable.pause_but)
                startGetCurrentPositionTask()

            }
        }
        player.setConsumers(
            onPreparedListenerConsumer,
            onCompletionListenerConsumer,
            pauseConsumer,
            startConsumer
        )
        player.preparePlayer()
    }

    override fun onPause() {
        super.onPause()
        player.pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
        killCurrentPositionTask()
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        player.playbackControl()
    }

    private fun backButWorking() {
        val back = findViewById<Button>(R.id.playerPreview)
        back.setOnClickListener {
            finish()
        }
    }

    private fun playBut() {
        binding.playBut.setOnClickListener {
            player.playbackControl()
        }
    }

    private fun createUpdateCurrentPositionTask(): Runnable {
        return object : Runnable {
            override fun run() {

                binding.playTime.text = PlayerTimeFormatter.format(player.currentPosition())

                mainThreadHandler.postDelayed(
                    this,
                    DEBOUNCE_GET_CURRENT_POSITION
                )
            }
        }
    }

    private fun startGetCurrentPositionTask() {
        mainThreadHandler.post(
            currentPositionTask
        )
    }

    private fun killCurrentPositionTask() {
        mainThreadHandler.removeCallbacks(
            currentPositionTask
        )
    }

    companion object {
        private const val DEBOUNCE_GET_CURRENT_POSITION = 300L
    }
}