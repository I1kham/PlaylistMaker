package com.alchemtech.playlistmaker.presentation.ui.player.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alchemtech.playlistmaker.domain.db.PlayListInteractor
import com.alchemtech.playlistmaker.domain.db.TracksDbInteractor
import com.alchemtech.playlistmaker.domain.entity.PlayList
import com.alchemtech.playlistmaker.domain.entity.Track
import kotlinx.coroutines.launch

class AddTrackToPlayListViewModel(
    private val playListInteractor: PlayListInteractor,
    private val tracksDbInteractor: TracksDbInteractor,

    ) : ViewModel() {
    var track: Track? = null
    private val stateLiveData = MutableLiveData<AddTrackToPlayListFragmentState>()

    companion object


    fun observeRenderState(): LiveData<AddTrackToPlayListFragmentState> = stateLiveData
    private fun renderState(state: AddTrackToPlayListFragmentState) {
        stateLiveData.postValue(state)
    }

    fun setTrackID(id: String?) {
        viewModelScope.launch {
            id?.let {
                track = tracksDbInteractor.getTrackById(it)
            }
        }
        startLogic()
    }

    internal fun addTrackTo(playList: PlayList) {
        var added = false
        viewModelScope.launch {
            track?.let {
                added = playListInteractor.addToList(
                    playList.id,
                    it.trackId
                )
            }
        }.invokeOnCompletion {
            renderState(
                AddTrackToPlayListFragmentState.TrackAdded(
                    added, playList.name
                )
            )
        }
    }

    private fun startLogic() {
        renderState(AddTrackToPlayListFragmentState.Loading(true))
        viewModelScope.launch {
            playListInteractor.getAllPlayLists().collect { listPlayList ->
                if (listPlayList.isNotEmpty()) {
                    renderState(AddTrackToPlayListFragmentState.ShowList(listPlayList))
                } else {
                    renderState(AddTrackToPlayListFragmentState.Empty)
                }
            }
        }
    }
}