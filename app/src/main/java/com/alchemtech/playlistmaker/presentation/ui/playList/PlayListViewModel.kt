package com.alchemtech.playlistmaker.presentation.ui.playList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alchemtech.playlistmaker.domain.db.PlayListInteractor
import com.alchemtech.playlistmaker.domain.sharing.SharePlayListInteractor
import kotlinx.coroutines.launch

class PlayListViewModel(
    private val playListInteractor: PlayListInteractor,
    private val sharePlayListInteractor: SharePlayListInteractor,
) : ViewModel() {
    private val stateLiveData = MutableLiveData<PlayListFragmentState>()
    private var playListId: Long? = null
    private var playListDuration: Long = 0L
    private var playListName: String? = null

    fun sharePlayList() {
        viewModelScope.launch {
            playListId?.let {
                sharePlayListInteractor.sharePlayList(it)
            }
        }
    }

    internal fun getPlayList(playListId: Long?) {
        this.playListId = playListId
        this.playListId?.let {
            viewModelScope.launch {
                playListDuration = 0
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

    fun deletePlayList(id: Long) {
        viewModelScope.launch {
            playListName?.let {
                renderState(PlayListFragmentState.Message(it))
            }
            playListInteractor.removePlayList(id)
            renderState(PlayListFragmentState.Exit)
        }
    }

    fun deleteTrack(trackId: Long, name: String) {
        viewModelScope.launch {
            playListId?.let {
                playListInteractor.removeFromList(it, trackId).let { getPlayList(playListId)
                renderState(PlayListFragmentState.DelMessage(
                        name
                    ))}
            }
        }
    }

    fun observeRenderState(): LiveData<PlayListFragmentState> = stateLiveData
    private fun renderState(state: PlayListFragmentState) {
        stateLiveData.postValue(state)
    }
}