package com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.model

import com.alchemtech.playlistmaker.domain.entity.Track

interface FavoriteTracksViewState {
    data object Empty : FavoriteTracksViewState
    data class TracksList(val tracks: kotlin.collections.List<Track>) : FavoriteTracksViewState
}