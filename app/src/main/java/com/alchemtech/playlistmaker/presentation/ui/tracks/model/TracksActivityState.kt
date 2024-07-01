package com.alchemtech.playlistmaker.presentation.ui.tracks.model

import com.alchemtech.playlistmaker.domain.entity.Track

sealed interface TracksActivityState {

    data object Loading : TracksActivityState
    data class TextClearBut(val visibility: Boolean) : TracksActivityState
    data class Content(val tracks: List<Track>) : TracksActivityState
    data class History(val tracks: List<Track>) : TracksActivityState
    data class Error(val errorMsg: Int) : TracksActivityState
    data class InputTextClear(val tracks: List<Track>) : TracksActivityState
    data object Exit : TracksActivityState

}