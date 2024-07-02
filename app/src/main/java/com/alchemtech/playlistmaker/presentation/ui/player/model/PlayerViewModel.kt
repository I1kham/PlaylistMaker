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

/*Player*/
class PlayerViewModel(
    application: Application,
    val track: Track,
    val player: PlayerInteractor,
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

        private const val DEBOUNCE_GET_CURRENT_POSITION = 300L
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
        killCurrentPositionTask()
    }

    // TODO: сюда функции
    private val currentPositionTask = createUpdateCurrentPositionTask()
    private var mainThreadHandler = Handler(Looper.getMainLooper())
    private val stateLiveData = MutableLiveData<PlayerState>()
    fun observeState(): LiveData<PlayerState> = stateLiveData
    private fun renderState(state: PlayerState) {
        stateLiveData.postValue(state)
        stateLiveData.value
    }

    init {
        preparePlayer()
    }

    private fun preparePlayer() {

        val onPreparedListenerConsumer =
            PlayerRepository.OnPreparedListenerConsumer {
                renderState(PlayerState.OnPrepared(track))
            }

        val onCompletionListenerConsumer =
            PlayerRepository.OnCompletionListenerConsumer {
                renderState(PlayerState.OnCompletion)
                killCurrentPositionTask()
            }


        val pauseConsumer = object : PlayerInteractor.PauseConsumer {
            override fun consume() {
                renderState(PlayerState.Pause)
                killCurrentPositionTask()
            }
        }
        val startConsumer = object : PlayerInteractor.StartConsumer {
            override fun consume() {
                renderState(PlayerState.Play)
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
                renderState(PlayerState.SetPlayTime(PlayerTimeFormatter.format(player.currentPosition())))
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

    internal fun backBut() {
        renderState(PlayerState.BackBut)
    }
}