package com.alchemtech.playlistmaker.presentation.ui.player

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.domain.models.Track
import com.alchemtech.playlistmaker.presentation.ui.UiCalculator
import java.text.SimpleDateFormat
import java.util.Locale

@Suppress("DEPRECATION")
open class PlayerActivity : AppCompatActivity(), UiCalculator {


    private var playerState = STATE_DEFAULT

    private var mediaPlayer = MediaPlayer()

    var mainThreadHandler = Handler(Looper.getMainLooper())

    private val currentPositionTask = createUpdateCurrentPositionTask()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backButWorking()

        val track = intent.getSerializableExtra("track") as Track

        PlayerFilling(track = track, binding, this)

        preparePlayer()

        playButWorking()
    }

    private fun killCurrentPositionTask() {
        mainThreadHandler.removeCallbacks(
            currentPositionTask
        )
    }

    private fun createUpdateCurrentPositionTask(): Runnable {
        return object : Runnable {
            override fun run() {

                val currentPosition = SimpleDateFormat(
                    "mm:ss",
                    Locale.getDefault()
                ).format(mediaPlayer.currentPosition)
                val playTime = findViewById<TextView>(R.id.playTime)

                playTime.text = currentPosition

                mainThreadHandler.postDelayed(this, DEBOUNCE_GET_CURRENT_POSITION)

            }
        }
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun playButWorking() {
        findViewById<ImageView>(R.id.playBut).setOnClickListener {
            playbackControl()
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        val play = findViewById<ImageView>(R.id.playBut)
        play.setImageResource(R.drawable.pause_but)
        playerState = STATE_PLAYING
        startGetCurrentPositionTask()
    }

    private fun startGetCurrentPositionTask() {
        mainThreadHandler.post(
            currentPositionTask
        )
    }

    private fun playbackControl() {
        when (playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }

            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        val play = findViewById<ImageView>(R.id.playBut)
        play.setImageResource(R.drawable.play_but)
        playerState = STATE_PAUSED
        killCurrentPositionTask()
    }

    private fun preparePlayer() {
        val track = intent.getSerializableExtra("track") as Track
        val play = findViewById<ImageView>(R.id.playBut)

        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            play.isEnabled = true
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            killCurrentPositionTask()
            val playTime = findViewById<TextView>(R.id.playTime)
            playTime.text = "00:00"
            play.setImageResource(R.drawable.play_but)
            playerState = STATE_PREPARED

        }
    }

    private fun backButWorking() {
        val back = findViewById<Button>(R.id.playerPreview)
        back.setOnClickListener {
            finish()
        }
    }

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3

        private const val DEBOUNCE_GET_CURRENT_POSITION = 300L

    }
}

