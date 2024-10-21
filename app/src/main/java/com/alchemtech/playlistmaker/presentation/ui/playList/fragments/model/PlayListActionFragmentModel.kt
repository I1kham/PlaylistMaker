package com.alchemtech.playlistmaker.presentation.ui.playList.fragments.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alchemtech.playlistmaker.domain.db.PlayListInteractor
import com.alchemtech.playlistmaker.presentation.ui.playList.fragments.state.PlayListActionFragmentState
import kotlinx.coroutines.launch

class PlayListActionFragmentModel(val playListInteractor: PlayListInteractor) : ViewModel() {
    private val stateLiveData = MutableLiveData<PlayListActionFragmentState>()
    private var playListId: Long? = null

    internal fun getPlayList(playListId: Long?) {
        this.playListId = playListId
        this.playListId?.let {
            viewModelScope.launch {
                playListInteractor.getPlayList(it).collect {
                    renderState(PlayListActionFragmentState.Content(it))
                }
            }
        }
    }

    fun deletePlayList(id: Long?) {
        viewModelScope.launch {
            id?.let {
                playListInteractor.removePlayList(it)
                renderState(PlayListActionFragmentState.Exit)
            }
        }
    }

    fun observeRenderState(): LiveData<PlayListActionFragmentState> = stateLiveData
    private fun renderState(state: PlayListActionFragmentState) {
        stateLiveData.postValue(state)
    }
}