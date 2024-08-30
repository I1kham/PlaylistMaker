package com.alchemtech.playlistmaker.presentation.ui.player.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alchemtech.playlistmaker.domain.api.PlayerRepository
import com.alchemtech.playlistmaker.domain.api.SingleTrackInteractor
import com.alchemtech.playlistmaker.domain.db.FavoriteTracksInteractor
import com.alchemtech.playlistmaker.domain.player.PlayerInteractor
import com.alchemtech.playlistmaker.presentation.ui.PlayerTimeFormatter
import com.alchemtech.playlistmaker.util.debounce
import kotlinx.coroutines.launch

class PlayerViewModel(
    singleTrackRepository: SingleTrackInteractor,
    private val player: PlayerInteractor,
    private val favoriteTracksInteractor: FavoriteTracksInteractor,
) : ViewModel() {
    private val track = singleTrackRepository.readTrack()
    private val stateLiveData = MutableLiveData<PlayerState>()
    init {
        preparePlayer()
        renderState(PlayerState.Fill(track!!))
    }
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

    internal fun onFavoriteClicked() {
        if (track!!.isFavorite) {
            track.isFavorite = false
            viewModelScope.launch {
                favoriteTracksInteractor.removeFromFavoriteList(track)
            }
        } else {
            track.isFavorite = true
            viewModelScope.launch {
                favoriteTracksInteractor.addToFavoriteList(track)
            }
        }
        renderState(PlayerState.likeBut(track.isFavorite))
    }

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