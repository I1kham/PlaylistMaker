package com.alchemtech.playlistmaker.presentation.ui.playList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alchemtech.playlistmaker.domain.db.PlayListInteractor
import kotlinx.coroutines.launch

class PlayListViewModel(
    private val playListInteractor: PlayListInteractor,
) : ViewModel() {

    private val stateLiveData = MutableLiveData<PlayListFragmentState>()


    private var playListId: Long? = null

    internal fun getPlayList(playListId: Long?) {
        this.playListId = playListId
        this.playListId?.let {
            viewModelScope.launch {
                renderState(PlayListFragmentState.Content(playListInteractor.getPlayList(it)))
            }
        }
    }

    fun observeRenderState(): LiveData<PlayListFragmentState> = stateLiveData
    private fun renderState(state: PlayListFragmentState) {
        stateLiveData.postValue(state)
    }
}