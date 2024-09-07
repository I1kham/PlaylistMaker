package com.alchemtech.playlistmaker.presentation.ui.addPlayList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddPlayListViewModel(

) : ViewModel() {
    private val stateLiveData = MutableLiveData<AddPlayListState>()

    fun observeRenderState(): LiveData<AddPlayListState> = stateLiveData
    private fun renderState(state: AddPlayListState) {
        stateLiveData.postValue(state)
    }

}