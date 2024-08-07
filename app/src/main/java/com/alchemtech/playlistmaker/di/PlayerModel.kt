package com.alchemtech.playlistmaker.di

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer

import com.alchemtech.playlistmaker.data.repository.PlayerRepositoryImpl
import com.alchemtech.playlistmaker.data.repository.SharedHistoryRepositoryImpl
import com.alchemtech.playlistmaker.domain.api.HistoryRepository
import com.alchemtech.playlistmaker.domain.api.PlayerRepository
import com.alchemtech.playlistmaker.domain.api.SingleTrackInteractor
import com.alchemtech.playlistmaker.domain.impl.SingleTrackInteractorImpl
import com.alchemtech.playlistmaker.domain.player.PlayerInteractor
import com.alchemtech.playlistmaker.domain.player.PlayerInteractorImlp
import com.alchemtech.playlistmaker.presentation.ui.player.model.PlayerViewModel
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
private const val SAVED_TRACK = "SAVED_TRACK"
private const val TRACK = "TRACK"

val playerViewModel = module {
    viewModel<PlayerViewModel> {
        PlayerViewModel(
            singleTrackRepository = this.get(),
            player = get()
        )
    }
    single<SingleTrackInteractor> {
        SingleTrackInteractorImpl(historyRepository = this.get())
    }
    factory<PlayerInteractor> {
        PlayerInteractorImlp(playerRepository = get())
    }
    factory<PlayerRepository> {
        PlayerRepositoryImpl(mediaPlayer = get())
    }
    factory<MediaPlayer> {
        MediaPlayer()
    }
    factory<HistoryRepository> {
        SharedHistoryRepositoryImpl(
            SAVED_TRACK,
            sharedPreferences = this.get(),
            gson = get()
        )
    }
    factory<SharedPreferences> {
        androidContext().getSharedPreferences(
            TRACK, Context.MODE_PRIVATE
        )
    }
    single<Gson> {
        Gson()
    }
}
