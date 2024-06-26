package com.alchemtech.playlistmaker.presentation.ui.player

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.ui.TrackUtils.convertFromString
import com.alchemtech.playlistmaker.presentation.ui.player.model.PlayerActivityViewModel

/*Player*/
class PlayerrActivity : ComponentActivity() {

    private lateinit var track: Track
    private lateinit var viewModel: PlayerActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getTrackFromIntent()
        viewModel = ViewModelProvider(
            this,
            PlayerActivityViewModel.getViewModelFactory(track)
        )[PlayerActivityViewModel::class.java]
    }

    private fun getTrackFromIntent() {

        track = convertFromString(intent.getSerializableExtra("track").toString())

    }
}