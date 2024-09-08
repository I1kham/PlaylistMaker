package com.alchemtech.playlistmaker.presentation.ui.addPlayList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alchemtech.playlistmaker.domain.db.PlayListInteractor

class AddPlayListViewModel(private val playListInteractor: PlayListInteractor

) : ViewModel() {
    private val stateLiveData = MutableLiveData<AddPlayListState>()

    fun observeRenderState(): LiveData<AddPlayListState> = stateLiveData
    private fun renderState(state: AddPlayListState) {
        stateLiveData.postValue(state)
    }






}