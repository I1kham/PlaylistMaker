package com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alchemtech.playlistmaker.domain.db.PlayListInteractor
import com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.state.PlayListsState
import kotlinx.coroutines.launch

class PlayListsViewModel(
    private val playListInteractor: PlayListInteractor,
) : ViewModel() {
    private val stateLiveData = MutableLiveData<PlayListsState>()

    init {
        getFavoriteTracksList()
    }

    fun observeState(): LiveData<PlayListsState> = stateLiveData

    private fun getFavoriteTracksList() {
        renderState(PlayListsState.Loading)
        viewModelScope.launch {

            playListInteractor.getAllPlayLists().collect { playList ->
                if (playList.isNotEmpty()) {
                    renderState(PlayListsState.ShowList(playList))
                } else {
                    renderState(PlayListsState.EmptyList)
                }
            }
        }
    }

    private fun renderState(state: PlayListsState) {
        stateLiveData.postValue(state)
    }
}