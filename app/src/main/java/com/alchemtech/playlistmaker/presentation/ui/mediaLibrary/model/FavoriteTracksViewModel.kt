package com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alchemtech.playlistmaker.domain.api.SingleTrackInteractor
import com.alchemtech.playlistmaker.domain.db.FavoriteTracksInteractor
import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.launch

class FavoriteTracksViewModel(
    private val favoriteTracksInteractor: FavoriteTracksInteractor,
    private val singleTrackInteractor: SingleTrackInteractor,
) :
    ViewModel() {
    private var tracksList = MutableLiveData<List<Track>>(listOf())
    private val stateLiveData = MutableLiveData<FavoriteTracksViewState>()

    init {
        startModelLogic()
    }

    fun observeState(): LiveData<FavoriteTracksViewState> = stateLiveData

    internal fun clickOnTrack(track: Track) {
        singleTrackInteractor.writeTrack(track)
    }

    private fun startModelLogic() {
        onTrackslistChange()
        getFavoriteTRacksList()
    }

    private fun onTrackslistChange() {
        tracksList.observeForever {
            if (!it.isNullOrEmpty()) {
                renderState(FavoriteTracksViewState.TracksList(it.asReversed()))
            } else {
                renderState(FavoriteTracksViewState.Empty)
            }
        }
    }

    private fun getFavoriteTRacksList() {
        renderState(FavoriteTracksViewState.Loading)
        viewModelScope.launch {
            favoriteTracksInteractor.getFavoriteTrackList().collect {
                tracksList.postValue(it)
            }
        }
    }

    private fun renderState(state: FavoriteTracksViewState) {
        stateLiveData.postValue(state)
    }
}