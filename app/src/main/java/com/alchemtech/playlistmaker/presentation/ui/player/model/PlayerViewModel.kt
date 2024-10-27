package com.alchemtech.playlistmaker.presentation.ui.player.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alchemtech.playlistmaker.domain.api.PlayerRepository
import com.alchemtech.playlistmaker.domain.db.TracksDbInteractor
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.domain.player.PlayerInteractor
import com.alchemtech.playlistmaker.presentation.ui.playerTimeFormatter
import com.alchemtech.playlistmaker.util.debounce
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val player: PlayerInteractor,
    private val tracksDbInteractor: TracksDbInteractor,
    ) : ViewModel() {
    private var playTrack: Track? = null
    private val stateLiveData = MutableLiveData<PlayerState>()

    companion object {
        private const val DEBOUNCE_GET_CURRENT_POSITION = 300L
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }

    internal fun prepareModel(trackId: String?) {
        viewModelScope.launch {
            trackId?.let { it ->
                playTrack = tracksDbInteractor.getTrackById(it)
                playTrack?.let {
                    preparePlayer(it)
                    renderState(PlayerState.Fill(it))
                }
            } ?: run {
                renderState(PlayerState.Error)
            }
        }
    }

    fun observeRenderState(): LiveData<PlayerState> = stateLiveData
    private fun renderState(state: PlayerState) {
        stateLiveData.postValue(state)
    }

    internal fun onPause() {
        player.pausePlayer()
    }

    internal fun onFavoriteClicked() {
        playTrack?.let {
            if (it.isFavorite) {
                it.isFavorite = false
                viewModelScope.launch {
                    tracksDbInteractor.unLikeTrack(it.trackId)
                }
            } else {
                it.isFavorite = true
                viewModelScope.launch {
                    tracksDbInteractor.likeTrack(it.trackId)
                }
            }
            renderState(PlayerState.LikeBut(it))
        }
    }


    internal fun playBut() {
        player.playbackControl()
    }

    private val statePosition = MutableLiveData<String>()

    internal fun observeCurrentPosition(): LiveData<String> = statePosition
    private fun renderPosition(state: String) {
        statePosition.postValue(state)
    }

    private fun preparePlayer(track: Track?) {
        renderState(PlayerState.Preparing(true))
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
            player.preparePlayer(it.previewUrl!!)
        }
    }

    private fun currentPositionTask() {
        run(debounce<Any>(
            delayMillis = DEBOUNCE_GET_CURRENT_POSITION,
            coroutineScope = viewModelScope,
            useLastParam = false
        ) {
            if (player.playerIsPlaying()) {
                renderPosition(playerTimeFormatter(player.currentPosition()))
                currentPositionTask()
            }
        })
    }
}