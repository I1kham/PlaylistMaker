package com.alchemtech.playlistmaker.presentation.ui.playList.fragments.state

import com.alchemtech.playlistmaker.domain.entity.Track

sealed interface TracksRecycleFragmentState {
data class Content(val tracks:List<Track>): TracksRecycleFragmentState
data object Empty: TracksRecycleFragmentState
}