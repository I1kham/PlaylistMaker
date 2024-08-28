package com.alchemtech.playlistmaker.di

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.room.Room
import com.alchemtech.playlistmaker.data.converters.TrackDbConvertor
import com.alchemtech.playlistmaker.data.db.entity.AppDatabase
import com.alchemtech.playlistmaker.data.db.entity.TrackDao
import com.alchemtech.playlistmaker.data.db.favoriteListRepo.FavoriteTracksRepositoryImpl
import com.alchemtech.playlistmaker.data.repository.PlayerRepositoryImpl
import com.alchemtech.playlistmaker.data.repository.SharedHistoryRepositoryImpl
import com.alchemtech.playlistmaker.domain.api.HistoryRepository
import com.alchemtech.playlistmaker.domain.api.PlayerRepository
import com.alchemtech.playlistmaker.domain.api.SingleTrackInteractor
import com.alchemtech.playlistmaker.domain.db.FavoriteTracksInteractor
import com.alchemtech.playlistmaker.domain.db.FavoriteTracksRepository
import com.alchemtech.playlistmaker.domain.impl.FavoriteTracksInteractorImpl
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
            player = get(),
            favoriteTracksInteractor = this.get(),
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
    single<FavoriteTracksInteractor> {
        FavoriteTracksInteractorImpl(
            favoriteTracksRepository = get()
        )
    }
    single<FavoriteTracksRepository> {
        FavoriteTracksRepositoryImpl(
            favoriteTRacksDao = get(),
            trackDbConvertor = get()
        )
    }
    single<AppDatabase> {
        Room
            .databaseBuilder(
                context = androidContext(),
                klass = AppDatabase::class.java,
                name = AppDatabase.NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }
    single<TrackDao> { get<AppDatabase>().trackDao() }

    single<TrackDbConvertor> {
        TrackDbConvertor()
    }
}