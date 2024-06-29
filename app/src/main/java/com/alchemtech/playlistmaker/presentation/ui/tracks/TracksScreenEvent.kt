package com.alchemtech.playlistmaker.presentation.ui.tracks

sealed class TracksScreenEvent {

    object HideKeyboard : TracksScreenEvent()
    object OpenPlayerScreen : TracksScreenEvent()
    object ClearSearch : TracksScreenEvent()
}