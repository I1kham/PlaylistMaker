package com.alchemtech.playlistmaker.presentation.ui.player.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alchemtech.playlistmaker.domain.entity.Track

/*Player*/
class PlayerActivityViewModel (private val track: Track,
) : ViewModel() {

    init {
        Log.d("TEST", "init!: $track")
    }

    companion object {
        fun getViewModelFactory(track: Track): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                // 1
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PlayerActivityViewModel(
                        track
                    ) as T
                }
            }
    }
}