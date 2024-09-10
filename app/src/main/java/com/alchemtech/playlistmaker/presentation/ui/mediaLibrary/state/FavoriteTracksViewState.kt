package com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.state

import com.alchemtech.playlistmaker.domain.entity.Track

sealed interface FavoriteTracksViewState {
    data object Loading : FavoriteTracksViewState
    data object Empty : FavoriteTracksViewState
    data class TracksList(val tracks: List<Track>) : FavoriteTracksViewState
}