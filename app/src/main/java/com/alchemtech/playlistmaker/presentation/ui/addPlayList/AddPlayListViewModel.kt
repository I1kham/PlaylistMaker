package com.alchemtech.playlistmaker.presentation.ui.addPlayList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alchemtech.playlistmaker.domain.db.PlayListInteractor
import com.alchemtech.playlistmaker.domain.entity.PlayList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddPlayListViewModel(
    private val playListInteractor: PlayListInteractor,
) : ViewModel() {
    private val stateLiveData = MutableLiveData<AddPlayListState>()
    private var playListName: String? = null
    private var playListDescription: String? = null
    private var uri: String? = null
    private var playListIdVm: Long = 0

    fun observeRenderState(): LiveData<AddPlayListState> = stateLiveData

    fun addPlayList() {
        playListName?.let {
            renderState(AddPlayListState.Loading)
            viewModelScope.launch {
                playListInteractor.addPlayList(
                    PlayList(
                        playListIdVm,
                        name = it,
                        description = playListDescription,
                        uri,
                    )
                )
                renderState(AddPlayListState.Exit(it))
            }
        }
    }

    fun savePlaylist() {
        playListName?.let {
            viewModelScope.launch {
                withContext(Dispatchers.IO){
                renderState(AddPlayListState.Loading)
                playListInteractor.updatePlaylistInfo(
                    playListIdVm,
                    it,
                    playListDescription,
                    uri
                )}
            }.invokeOnCompletion {
                renderState(AddPlayListState.Exit(playListName!!))
            }
        }
    }

    internal fun editPlaylist(playListID: Long?) {
        playListID?.let {
            viewModelScope.launch {
                playListInteractor.getPlayList(it).let {
                    uri = it.coverUri
                    playListIdVm = playListID
                    renderState(AddPlayListState.Content(it))
                }
            }
        }
    }


    internal fun setUri(uri: String?) {
        this.uri = uri
        renderState(AddPlayListState.SetPic(uri))
    }

    internal fun setName(name: String) {
        this.playListName = name
    }

    internal fun setDescription(description: String) {
        this.playListDescription = description
    }

    private fun renderState(state: AddPlayListState) {
        stateLiveData.postValue(state)
    }
}