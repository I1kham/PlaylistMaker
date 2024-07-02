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
) : AndroidViewModel(application) {

    companion object {
        fun getViewModelFactory(
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PlayerViewModel(
                    this[APPLICATION_KEY] as Application
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
    private var track: Track? = SingleTrackRepositoryCreator.provideSingleTrackDb().readTrack()
    private val currentPositionTask = createUpdateCurrentPositionTask()
    var mainThreadHandler = Handler(Looper.getMainLooper())
    private val stateLiveData = MutableLiveData<PlayerState>()
    fun observeState(): LiveData<PlayerState> = stateLiveData
    private fun renderState(state: PlayerState) {
        stateLiveData.postValue(state)
        stateLiveData.value
    }

    internal fun fill() {
        renderState(PlayerState.FillViewWithTrackData)
        preparePlayer()
    }

    private lateinit var player: PlayerInteractor

    private fun preparePlayer() {
        if (track != null) {
            PlayerCreator.providePlayer(track!!).also { player = it }

            val onPreparedListenerConsumer =
                PlayerRepository.OnPreparedListenerConsumer {
                    renderState(PlayerState.OnPrepared(track!!))
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