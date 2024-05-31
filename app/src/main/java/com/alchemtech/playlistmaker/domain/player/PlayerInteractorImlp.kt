package com.alchemtech.playlistmaker.domain.player

import android.media.MediaPlayer // TODO: to data
import android.os.Handler // TODO: presentation
import android.os.Looper//TODO: presentation
import com.alchemtech.playlistmaker.R//TODO: presentation
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding//TODO: presentation
import com.alchemtech.playlistmaker.domain.entity.Track
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerInteractorImlp(
    private val binding: ActivityPlayerBinding,
    private val track: Track,
) :
    PlayerInteractor {


    private var playerState: Int = STATE_DEFAULT
    private var mediaPlayer = MediaPlayer()

    private val currentPositionTask = createUpdateCurrentPositionTask()
    var mainThreadHandler = Handler(Looper.getMainLooper())

    init {
        preparePlayer()
    }

    override fun pausePlayer() {
        mediaPlayer.pause()
        playerState = STATE_PAUSED
        binding.playBut.setImageResource(R.drawable.play_but)
        killCurrentPositionTask()
    }

    override fun startPlayer() {
        mediaPlayer.start()
        playerState = STATE_PLAYING
        binding.playBut.setImageResource(R.drawable.pause_but)
        startGetCurrentPositionTask()
    }

    override fun release() {
        mediaPlayer.release()
        killCurrentPositionTask()
    }

    override fun playbackControl() {
        when (playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }

            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }

    fun preparePlayer() {
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = STATE_PREPARED
            binding.playBut.isEnabled = true
        }
        mediaPlayer.setOnCompletionListener {
            killCurrentPositionTask()
            binding.playTime.text = "00:00"
            binding.playBut.setImageResource(R.drawable.play_but)
            playerState = STATE_PREPARED
        }
    }

    private fun createUpdateCurrentPositionTask(): Runnable {
        return object : Runnable {
            override fun run() {

                val currentPosition = SimpleDateFormat(
                    "mm:ss",
                    Locale.getDefault()
                ).format(mediaPlayer.currentPosition)

                binding.playTime.text = currentPosition

                mainThreadHandler.postDelayed(this, DEBOUNCE_GET_CURRENT_POSITION)

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
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val DEBOUNCE_GET_CURRENT_POSITION = 300L
    }
}