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
        println(id)
        viewModelScope.launch {
            id?.let {
                track = tracksDbInteractor.getTrackById(it)
                println("AddTrackToPlayListViewModel "+track)
            }
        }
        startLogic()
    }

    internal fun addTrackTo(playList: PlayList) {
        viewModelScope.launch {
            track?.let {
                renderState(
                    AddTrackToPlayListFragmentState.TrackAdded(
                        playListInteractor.addToList(
                            playList.id,
                            it
                        ), playList.name
                    )
                )
            }
        }
    }
    private fun startLogic() {
        viewModelScope.launch {
            renderState(AddTrackToPlayListFragmentState.Loading(true))
            playListInteractor.getAllPlayLists().collect { playList ->
                if (playList.isNotEmpty()) {
                    renderState(AddTrackToPlayListFragmentState.ShowList(playList))
                } else {
                    renderState(AddTrackToPlayListFragmentState.Empty)
                }
            }
        }
    }



}