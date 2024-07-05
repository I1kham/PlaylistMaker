package com.alchemtech.playlistmaker.presentation.ui.tracks.model

import com.alchemtech.playlistmaker.domain.entity.Track

sealed interface TracksState {

    data object Loading : TracksState
    data class TextClearBut(val visibility: Boolean) : TracksState
    data class Content(val tracks: List<Track>) : TracksState
    data class History(val tracks: List<Track>) : TracksState
    data class Error(val errorCode: Int) : TracksState
    data class InputTextClear(val tracks: List<Track>) : TracksState

}