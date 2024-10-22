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
    private var playListDuration: Long = 0L
    private var playListName: String? = null

    internal fun getPlayList(playListId: Long?) {
        this.playListId = playListId
        this.playListId?.let {
            viewModelScope.launch {
                val playList = playListInteractor.getPlayList(it)
                playListName = playList.name
                playList.tracks.map {
                    playListDuration += it.trackTimeMillis
                }
                renderState(
                    PlayListFragmentState.Content(
                        playList.coverUri,
                        playList.name,
                        playList.description,
                        playListDuration,
                        playList.tracks.size
                    )
                )
            }
        }
    }

    fun deletePlayList(id: Long) = viewModelScope.launch {
        playListName?.let {
            renderState(PlayListFragmentState.Deleted(it))
        }
        playListInteractor.removePlayList(id)
        renderState(PlayListFragmentState.Exit)
    }

    fun observeRenderState(): LiveData<PlayListFragmentState> = stateLiveData
    private fun renderState(state: PlayListFragmentState) {
        stateLiveData.postValue(state)
    }
}