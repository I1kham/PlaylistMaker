package com.alchemtech.playlistmaker.presentation.ui.player.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alchemtech.playlistmaker.domain.api.PlayerRepository
import com.alchemtech.playlistmaker.domain.api.SingleTrackInteractor
import com.alchemtech.playlistmaker.domain.db.FavoriteTracksInteractor
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.domain.player.PlayerInteractor
import com.alchemtech.playlistmaker.presentation.ui.PlayerTimeFormatter
import com.alchemtech.playlistmaker.util.debounce
import kotlinx.coroutines.launch

class PlayerViewModel(
    singleTrackRepository: SingleTrackInteractor,
    private val player: PlayerInteractor,
    private val favoriteTracksInteractor: FavoriteTracksInteractor,
) : ViewModel() {
    private var track: Track? = singleTrackRepository.readTrack()
    private val stateLiveData = MutableLiveData<PlayerState>()

    init {
        track?.let {
            preparePlayer()
            renderState(PlayerState.Fill(it))
        }
    }

    companion object {
        private const val DEBOUNCE_GET_CURRENT_POSITION = 300L
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }

    internal fun onPause() {
        player.pausePlayer()
    }

    internal fun onFavoriteClicked() {
        track?.let {
            if (it.isFavorite) {
                it.isFavorite = false
                viewModelScope.launch {
                    favoriteTracksInteractor.removeFromFavoriteList(it)
                }
            } else {
                it.isFavorite = true
                viewModelScope.launch {
                    favoriteTracksInteractor.addToFavoriteList(it)
                }
            }
            renderState(PlayerState.LikeBut(it))
        }
    }

    fun observeRenderState(): LiveData<PlayerState> = stateLiveData
    private fun renderState(state: PlayerState) {
        stateLiveData.postValue(state)
    }

    private val statePosition = MutableLiveData<String>()

    fun observeCurrentPosition(): LiveData<String> = statePosition
    private fun renderPosition(state: String) {
        statePosition.postValue(state)
    }

    private fun preparePlayer() {
        track?.let {
            val onPreparedListenerConsumer =
                PlayerRepository.OnPreparedListenerConsumer {
                    renderState(PlayerState.OnPrepared(it))
                }

            val onCompletionListenerConsumer =
                PlayerRepository.OnCompletionListenerConsumer {
                    renderState(PlayerState.OnCompletion(it))
                }

            val pauseConsumer = object : PlayerInteractor.PauseConsumer {
                override fun consume() {
                    renderState(PlayerState.Pause(it))
                }
            }
            val startConsumer = object : PlayerInteractor.StartConsumer {
                override fun consume() {
                    renderState(PlayerState.Play(it))
                    currentPositionTask()
                }
            }
            player.setConsumers(
                onPreparedListenerConsumer,
                onCompletionListenerConsumer,
                pauseConsumer,
                startConsumer
            )
            it.previewUrl?.let { player.preparePlayer(it) }
        }
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