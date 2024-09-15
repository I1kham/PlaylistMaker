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

    fun observeRenderState(): LiveData<AddPlayListState> = stateLiveData

    fun savePlayList() {
        if (playListName.isNotEmpty()) {
            renderState(AddPlayListState.Loading)
            viewModelScope.launch {
                playListInteractor.addPlayList(
                    PlayList(
                        name = playListName,
                        description = playListDescription,
                        uri,
                    )
                )
                renderState(AddPlayListState.Exit)
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