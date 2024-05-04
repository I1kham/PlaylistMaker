package com.alchemtech.playlistmaker

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alchemtech.playlistmaker.track.Track
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.Locale


@Suppress("DEPRECATION")
class PlayerActivity : AppCompatActivity() {
    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3

        private const val DEBOUNCE_GET_CURRENT_POSITION = 300L

    }

    private var playerState = STATE_DEFAULT

    private var mediaPlayer = MediaPlayer()

    var mainThreadHandler = Handler(Looper.getMainLooper())

    private val currentPositionTask = createUpdateCurrentPositionTask()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        backButWorking()

        val track = intent.getSerializableExtra("track") as Track

        trackTitleFill(track)

        trackArtist(track)

        trackTimeMillis(track)

        collectionName(track)

        releaseDateFill(track)

        countryFill(track)

        playTimeFill(track)

        albumCoverFill(track)

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


    private fun trackArtist(track: Track) {
        val trackArtist: TextView = findViewById(R.id.playerArtistName)
        trackArtist.text = track.artistName
    }

    private fun trackTimeMillis(track: Track) {
        val trackTimeMillis: TextView = findViewById(R.id.trackTimeMillisText)
        trackTimeMillis.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
    }

    private fun collectionName(track: Track) {
        val collectionName = findViewById<TextView>(R.id.collectionNameText)
        if (!track.collectionName.isNullOrEmpty()) {
            collectionName.text = track.collectionName
        } else {
            collectionName.visibility = View.GONE
            findViewById<TextView>(R.id.trackAlbum).visibility = View.GONE
        }
    }

    private fun releaseDateFill(track: Track) {
        val releaseDate = findViewById<TextView>(R.id.releaseDateText)
        releaseDate.text = track.releaseDate.substring(0 until 4) + " год"
        val primaryGenreName = findViewById<TextView>(R.id.primaryGenreNameText)
        primaryGenreName.text = track.primaryGenreName
    }

    private fun countryFill(track: Track) {
        val country = findViewById<TextView>(R.id.trackCountryText)
        country.text = track.country
    }

    private fun playTimeFill(track: Track) {
        val playTime = findViewById<TextView>(R.id.playTime)
        playTime.text = "0:30"
    }

    private fun albumCoverFill(track: Track) {
        val albumCover: ImageView = findViewById(R.id.playerAlbumCover)

        Glide.with(this)
            .load(track.getCoverArtwork())
            .placeholder(R.drawable.track_album_default_big)
            .centerCrop()
            .transform(
                RoundedCorners(
                    getPxFromDp(8f)
                )
            )
            .into(albumCover)

        val addTrack = findViewById<ImageView>(R.id.playerAddToListBut)
        addTrack.setOnClickListener {
        }

    }

    private fun trackTitleFill(track: Track) {
        val trackTitle: TextView = findViewById(R.id.playerTrackName)
        trackTitle.text = track.trackName

    }

    private fun getPxFromDp(dp: Float): Int {

        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            resources.displayMetrics
        ).toInt()
    }

    private fun backButWorking() {
        val back = findViewById<Button>(R.id.playerPreview)
        back.setOnClickListener {
            finish()
        }
    }

}

