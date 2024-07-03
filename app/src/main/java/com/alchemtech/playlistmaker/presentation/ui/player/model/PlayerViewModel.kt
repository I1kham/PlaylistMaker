package com.alchemtech.playlistmaker.presentation.ui.player.model

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alchemtech.playlistmaker.creators.PlayerCreator
import com.alchemtech.playlistmaker.creators.SingleTrackRepositoryCreator
import com.alchemtech.playlistmaker.domain.api.PlayerRepository
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.domain.player.PlayerInteractor
import com.alchemtech.playlistmaker.presentation.ui.PlayerTimeFormatter

class PlayerViewModel(
    application: Application,
    private val track: Track,
    private val player: PlayerInteractor,

    ) : AndroidViewModel(application) {

    companion object {
        fun getViewModelFactory(
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val track = SingleTrackRepositoryCreator.provideSingleTrackDb().readTrack()!!
                PlayerViewModel(
                    this[APPLICATION_KEY] as Application,
                    track,
                    PlayerCreator.providePlayer(track),
                )
            }
        }
        private const val DEBOUNCE_GET_CURRENT_POSITION = 250L
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
        killCurrentPositionTask()
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
       // renderState(PlayerState.Fill(track))
        preparePlayer()
    }

    private fun preparePlayer() {

        val onPreparedListenerConsumer =
            PlayerRepository.OnPreparedListenerConsumer {
                renderState(PlayerState.OnPrepared(track))
            }

        val onCompletionListenerConsumer =
            PlayerRepository.OnCompletionListenerConsumer {
                renderState(PlayerState.OnCompletion(track))
                killCurrentPositionTask()
            }


        val pauseConsumer = object : PlayerInteractor.PauseConsumer {
            override fun consume() {
                renderState(PlayerState.Pause(track))
                killCurrentPositionTask()
            }
        }
        val startConsumer = object : PlayerInteractor.StartConsumer {
            override fun consume() {
                renderState(PlayerState.Play(track))
                startGetCurrentPositionTask()
            }
        }
        player.setConsumers(
            onPreparedListenerConsumer,
            onCompletionListenerConsumer,
            pauseConsumer,
            startConsumer
        )
        player.preparePlayer()
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
             //   renderState(PlayerState.SetPlayTime(PlayerTimeFormatter.format(player.currentPosition())))
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