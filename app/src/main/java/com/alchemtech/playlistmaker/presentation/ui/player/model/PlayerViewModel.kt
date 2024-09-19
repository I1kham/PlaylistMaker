package com.alchemtech.playlistmaker.presentation.ui.player.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alchemtech.playlistmaker.domain.api.PlayerRepository
import com.alchemtech.playlistmaker.domain.api.SingleTrackInteractor
import com.alchemtech.playlistmaker.domain.db.FavoriteTracksInteractor
import com.alchemtech.playlistmaker.domain.db.PlayListInteractor
import com.alchemtech.playlistmaker.domain.entity.PlayList
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.domain.player.PlayerInteractor
import com.alchemtech.playlistmaker.presentation.ui.playerTimeFormatter
import com.alchemtech.playlistmaker.util.debounce
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val singleTrackRepository: SingleTrackInteractor,
    private val player: PlayerInteractor,
    private val favoriteTracksInteractor: FavoriteTracksInteractor,
    private val playListInteractor: PlayListInteractor,
) : ViewModel() {
    private var playTrack: Track? = singleTrackRepository.readTrack()
    private val stateLiveData = MutableLiveData<PlayerState>()

    companion object {
        private const val DEBOUNCE_GET_CURRENT_POSITION = 300L
    }

    init {
        playTrack?.let {
            preparePlayer()
            renderState(PlayerState.Fill(it))
        }
    }


    override fun onCleared() {
        super.onCleared()
        player.release()
    }

    fun observeRenderState(): LiveData<PlayerState> = stateLiveData
    private fun renderState(state: PlayerState) {
        stateLiveData.postValue(state)
    }

    internal fun onPause() {
        player.pausePlayer()
    }

    internal fun addTrackTo(playList: PlayList) {
        viewModelScope.launch {
            playTrack?.let {
                renderState(
                    PlayerState.TrackAdded(
                        playListInteractor.addToList(
                            playList.id,
                            it
                        ), playList.name
                    )
                )
            }
        }
    }

    internal fun onFavoriteClicked() {
        playTrack?.let {
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

    internal fun addToPlaylist() {
        viewModelScope.launch {
            playListInteractor.getAllPlayLists().collect { playList ->
                if (playList.isNotEmpty()) {
                    renderState(PlayerState.ShowList(playList))
                } else {
                    renderState(PlayerState.EmptyList)
                }
            }
        }
    }

    internal fun playBut() {
        player.playbackControl()
    }

    private val statePosition = MutableLiveData<String>()

    fun observeCurrentPosition(): LiveData<String> = statePosition
    private fun renderPosition(state: String) {
        statePosition.postValue(state)
    }

    private fun preparePlayer() {
        playTrack?.let {
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