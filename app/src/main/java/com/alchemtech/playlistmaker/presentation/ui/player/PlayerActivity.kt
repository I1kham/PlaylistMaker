package com.alchemtech.playlistmaker.presentation.ui.player

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.creators.PlayerCreator
import com.alchemtech.playlistmaker.creators.PlayerDataFillingCreator
import com.alchemtech.playlistmaker.databinding.ActivityPlayerBinding
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.domain.player.PlayerInteractor

@Suppress("DEPRECATION")
open class PlayerActivity : AppCompatActivity() {
    private var track: Track? = null
    private var binding: ActivityPlayerBinding? = null
    private var player: PlayerInteractor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        track = intent.getSerializableExtra("track") as Track
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        PlayerCreator.providePlayer(binding!!, track!!).also { player = it }
        PlayerDataFillingCreator.provide(this, binding!!, track!!)
        setContentView(binding!!.root)
        backButWorking()
        playBut()
    }

    override fun onPause() {
        super.onPause()
        player!!.pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        player!!.release()
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        player!!.playbackControl()
    }

    private fun backButWorking() {
        val back = findViewById<Button>(R.id.playerPreview)
        back.setOnClickListener {
            finish()
        }
    }

    private fun playBut() {
        binding!!.playBut.setOnClickListener {
            player!!.playbackControl()
        }
    }
}

