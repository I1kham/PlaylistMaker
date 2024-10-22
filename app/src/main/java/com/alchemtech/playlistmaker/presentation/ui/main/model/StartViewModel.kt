package com.alchemtech.playlistmaker.presentation.ui.main.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alchemtech.playlistmaker.domain.db.PlayListInteractor
import kotlinx.coroutines.launch

class StartViewModel(val playListInteractor: PlayListInteractor) : ViewModel() {
    fun cleanDb() {
        viewModelScope.launch {
            playListInteractor.cleaningDb()
        }
    }
}