package com.alchemtech.playlistmaker.presentation.ui.addPlayList

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alchemtech.playlistmaker.domain.db.PlayListInteractor
import com.alchemtech.playlistmaker.domain.entity.PlayList
import kotlinx.coroutines.launch

class AddPlayListViewModel(
    private val playListInteractor: PlayListInteractor,
) : ViewModel() {
    private val stateLiveData = MutableLiveData<AddPlayListState>()
    private var playListName: String = ""
    private var playListDescription: String? = null
    private var uri: Uri? = null
    private var playListIdVm: Long = 0

    fun observeRenderState(): LiveData<AddPlayListState> = stateLiveData

    fun addPlayList() {
        if (playListName.isNotEmpty()) {
            renderState(AddPlayListState.Loading)
            viewModelScope.launch {
                playListInteractor.addPlayList(
                    PlayList(
                        playListIdVm,
                        name = playListName,
                        description = playListDescription,
                        uri,
                    )
                )
                renderState(AddPlayListState.Exit(playListName))
            }
        }
    }

    fun savePlaylist() {
        viewModelScope.launch {
            renderState(AddPlayListState.Loading)
            playListInteractor.updatePlaylistInfo(
                playListIdVm,
                playListName,
                playListDescription,
                uri
            )
        }
        renderState(AddPlayListState.Exit(playListName))
    }


    internal fun editPlaylist(playListID: Long?) {
        playListID?.let {
            viewModelScope.launch {
                val playList = playListInteractor.getPlayList(it)
                uri = playList.coverUri
                playListIdVm = playListID
                renderState(AddPlayListState.Content(playList))
            }
        }
    }


    internal fun setUri(uri: Uri?) {
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