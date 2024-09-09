package com.alchemtech.playlistmaker.presentation.ui.addPlayList

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alchemtech.playlistmaker.domain.db.PlayListInteractor
import com.alchemtech.playlistmaker.domain.entity.PlayList
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AddPlayListViewModel(
    private val playListInteractor: PlayListInteractor,

    ) : ViewModel() {
    private val stateLiveData = MutableLiveData<AddPlayListState>()
    var uri: Uri? = null
    val playListName: String = "Name of the playlist"
    val playListDescription: String = "null"


    init {
        viewModelScope.launch {
          playListInteractor.getAllPlayLists().map { it.map { println("___"+it.name.toString()) } }
        }
    }

    fun observeRenderState(): LiveData<AddPlayListState> = stateLiveData
    private fun renderState(state: AddPlayListState) {
        stateLiveData.postValue(state)
    }

    fun onCeared() {

        viewModelScope.launch {
            playListInteractor.addPlayList(
                PlayList(
                    name = playListName,
                    description = "_____",
                    uri.toString(),
                )
            )
        }
        println("playListInteractor.addPlayList")
    }


    


}