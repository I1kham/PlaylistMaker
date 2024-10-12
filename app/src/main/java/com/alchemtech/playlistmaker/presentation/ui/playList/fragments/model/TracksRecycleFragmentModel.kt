package com.alchemtech.playlistmaker.presentation.ui.playList.fragments.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alchemtech.playlistmaker.domain.db.PlayListInteractor
import com.alchemtech.playlistmaker.presentation.ui.playList.fragments.state.TracksRecycleFragmentState
import kotlinx.coroutines.launch

class TracksRecycleFragmentModel(
    private val playListInteractor: PlayListInteractor,
) : ViewModel() {

    private val stateLiveData = MutableLiveData<TracksRecycleFragmentState>()


    internal fun getTracks(id: Long) {

        viewModelScope.launch {
            playListInteractor.getTracks(id).collect {
                if(it.isNotEmpty()){
                    renderState(TracksRecycleFragmentState.Content(it))
                }else{
                    renderState(TracksRecycleFragmentState.Empty)
                }

            }
        }
    }

    fun observeRenderState(): LiveData<TracksRecycleFragmentState> = stateLiveData
    private fun renderState(state: TracksRecycleFragmentState) {
        stateLiveData.postValue(state)
    }

}