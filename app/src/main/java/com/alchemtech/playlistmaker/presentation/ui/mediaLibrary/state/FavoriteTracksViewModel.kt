package com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alchemtech.playlistmaker.domain.api.SingleTrackInteractor
import com.alchemtech.playlistmaker.domain.db.TracksDbInteractor
import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.launch

class FavoriteTracksViewModel(
    private val tracksDbInteractor: TracksDbInteractor,
    private val singleTrackInteractor: SingleTrackInteractor,
) : ViewModel() {
    private val stateLiveData = MutableLiveData<FavoriteTracksViewState>()

    init {
        startModelLogic()
    }

    fun observeState(): LiveData<FavoriteTracksViewState> = stateLiveData

    fun clickOnTrack(track: Track) {
        singleTrackInteractor.writeTrack(track)
    }

    private fun startModelLogic() {
        getFavoriteTracksList()
    }

    private fun getFavoriteTracksList() {
        renderState(FavoriteTracksViewState.Loading)
        viewModelScope.launch {
            tracksDbInteractor.getFavoriteTrackList().collect { list ->
                if (list.isNotEmpty()) {
                    renderState(FavoriteTracksViewState.TracksList(list.asReversed()))
                } else {
                    renderState(FavoriteTracksViewState.Empty)
                }
            }
        }
    }

    private fun renderState(state: FavoriteTracksViewState) {
        stateLiveData.postValue(state)
    }
}