package com.alchemtech.playlistmaker.presentation.ui.player.model

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alchemtech.playlistmaker.creators.PlayerCreator
import com.alchemtech.playlistmaker.domain.api.PlayerRepository
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.domain.player.PlayerInteractor
import com.alchemtech.playlistmaker.presentation.ui.PlayerTimeFormatter

/*Player*/
class PlayerActivityViewModel(
    application: Application, private val track: Track,
) : AndroidViewModel(application) {

    init {
        Log.d("TEST", "init!: $track")
    }

    companion object {
        fun getViewModelFactory(track: Track): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PlayerActivityViewModel(
                    this[APPLICATION_KEY] as Application, track
                )
            }
        }
        private const val DEBOUNCE_GET_CURRENT_POSITION = 300L
    }
    // TODO: сюда функции
    private val currentPositionTask = createUpdateCurrentPositionTask()
    var mainThreadHandler = Handler(Looper.getMainLooper())
    private val stateLiveData = MutableLiveData<PlayerActivityState>()
    fun observeState(): LiveData<PlayerActivityState> = stateLiveData
    private fun renderState(state: PlayerActivityState) {
        stateLiveData.postValue(state)
        stateLiveData.value
    }
    internal fun fill(){
        renderState(PlayerActivityState.FillViewWithTrackData)
        preparePlayer()
    }

    private lateinit var player: PlayerInteractor

    private fun preparePlayer() {
        PlayerCreator.providePlayer(track).also { player = it }


        val onPreparedListenerConsumer =
            PlayerRepository.OnPreparedListenerConsumer {
                renderState(PlayerActivityState.OnPrepared)
            }

        val onCompletionListenerConsumer =
            PlayerRepository.OnCompletionListenerConsumer {
                renderState(PlayerActivityState.OnCompletion)
                killCurrentPositionTask()
//                binding.playTime.text = "00:00"
//                binding.playBut.setImageResource(R.drawable.play_but)
            }


        val pauseConsumer = object : PlayerInteractor.PauseConsumer {
            override fun consume() {
//                binding.playBut.setImageResource(R.drawable.play_but)
renderState(PlayerActivityState.Pause)
                killCurrentPositionTask()
            }
        }
        val startConsumer = object : PlayerInteractor.StartConsumer {
            override fun consume() {

                renderState(PlayerActivityState.Play)

//                binding.playBut.setImageResource(R.drawable.pause_but)
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
renderState(PlayerActivityState.SetPlayTime(PlayerTimeFormatter.format(player.currentPosition())))
//                binding.playTime.text = PlayerTimeFormatter.format(player.currentPosition())

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