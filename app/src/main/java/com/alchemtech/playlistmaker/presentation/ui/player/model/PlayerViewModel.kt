package com.alchemtech.playlistmaker.presentation.ui.player.model

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alchemtech.playlistmaker.domain.api.PlayerRepository
import com.alchemtech.playlistmaker.domain.api.SingleTrackInteractor
import com.alchemtech.playlistmaker.domain.player.PlayerInteractor
import com.alchemtech.playlistmaker.presentation.ui.PlayerTimeFormatter

class PlayerViewModel(
    singleTrackRepository: SingleTrackInteractor,
    private val player: PlayerInteractor,
    ) : ViewModel() {
   private val track = singleTrackRepository.readTrack()

    companion object {
        private const val DEBOUNCE_GET_CURRENT_POSITION = 250L
    }

    internal fun onStop() {
        super.onCleared()
        player.release()
        killCurrentPositionTask()
    }

    internal fun onPause(){
        player.pausePlayer()
    }

    private val currentPositionTask = createUpdateCurrentPositionTask()
    private var mainThreadHandler = Handler(Looper.getMainLooper())

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
                renderState(PlayerState.OnPrepared(track!!))
            }

        val onCompletionListenerConsumer =
            PlayerRepository.OnCompletionListenerConsumer {
                renderState(PlayerState.OnCompletion(track!!))
                killCurrentPositionTask()
            }

        val pauseConsumer = object : PlayerInteractor.PauseConsumer {
            override fun consume() {
                renderState(PlayerState.Pause(track!!))
                killCurrentPositionTask()
            }
        }
        val startConsumer = object : PlayerInteractor.StartConsumer {
            override fun consume() {
                renderState(PlayerState.Play(track!!))
                startGetCurrentPositionTask()
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

    private fun killCurrentPositionTask() {
        mainThreadHandler.removeCallbacks(
            currentPositionTask
        )
    }

    private fun createUpdateCurrentPositionTask(): Runnable {
        return object : Runnable {
            override fun run() {
                renderPosition(PlayerTimeFormatter.format(player.currentPosition()))
                mainThreadHandler.postDelayed(
                    this,
                    DEBOUNCE_GET_CURRENT_POSITION
                )
            }
        }
    }

    private fun startGetCurrentPositionTask() {
        mainThreadHandler.post(
            currentPositionTask
        )
    }

    internal fun playBut() {
        player.playbackControl()
    }
}