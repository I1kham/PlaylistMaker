package com.alchemtech.playlistmaker.presentation.ui.playList

import androidx.lifecycle.ViewModel
import com.alchemtech.playlistmaker.domain.db.PlayListInteractor

class PlayListViewModel(
    private val playListInteractor: PlayListInteractor,
) : ViewModel() {
    init {
        println("PlaylistViewModel")
    }
}