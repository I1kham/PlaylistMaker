package com.alchemtech.playlistmaker.presentation.ui.player.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alchemtech.playlistmaker.domain.api.PlayerRepository
import com.alchemtech.playlistmaker.domain.api.SingleTrackInteractor
import com.alchemtech.playlistmaker.domain.player.PlayerInteractor
import com.alchemtech.playlistmaker.presentation.ui.PlayerTimeFormatter
import com.alchemtech.playlistmaker.util.debounce

class PlayerViewModel(
    singleTrackRepository: SingleTrackInteractor,
    private val player: PlayerInteractor,
) : ViewModel() {
    private val track = singleTrackRepository.readTrack()

    companion object {
        private const val DEBOUNCE_GET_CURRENT_POSITION = 300L
    }

    internal fun onStop() {
        super.onCleared()
        player.release()
    }

    internal fun onPause() {
        player.pausePlayer()
    }

    private val stateLiveData = MutableLiveData<PlayerState>()

    fun observeRenderState(): LiveData<PlayerState> = stateLiveData
    private fun renderState(state: PlayerState) {
        stateLiveData.postValue(state)
        stateLiveData.value
    }

    private val statePosition = MutableLiveData<String>()

    fun observeCurrentPosition(): LiveData<String> = statePosition
    private fun renderPosition(state: String) {
        statePosition.postValue(state)
        statePosition.value
    }

    init {
        preparePlayer()
        renderState(PlayerState.Fill(track!!))
    }

    private fun preparePlayer() {

        val onPreparedListenerConsumer =
            PlayerRepository.OnPreparedListenerConsumer {
                renderState(PlayerState.OnPrepared)
            }

        val onCompletionListenerConsumer =
            PlayerRepository.OnCompletionListenerConsumer {
                renderState(PlayerState.OnCompletion)
            }

        val pauseConsumer = object : PlayerInteractor.PauseConsumer {
            override fun consume() {
                renderState(PlayerState.Pause)
            }
        }
        val startConsumer = object : PlayerInteractor.StartConsumer {
            override fun consume() {
                renderState(PlayerState.Play)
                currentPositionTask()
            }
        }
        player.setConsumers(
            onPreparedListenerConsumer,
            onCompletionListenerConsumer,
            pauseConsumer,
            startConsumer
        )
        track!!.previewUrl?.let { player.preparePlayer(it) }
    }

    private fun currentPositionTask() {
        run(debounce<Any>(
            delayMillis = DEBOUNCE_GET_CURRENT_POSITION,
            coroutineScope = viewModelScope,
            useLastParam = false
        ) {
            if (player.playerIsPlaying()) {
                currentPositionTask()
                renderPosition(PlayerTimeFormatter.format(player.currentPosition()))
            }
        })
    }

    internal fun playBut() {
        player.playbackControl()
    }
}