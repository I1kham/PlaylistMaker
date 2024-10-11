package com.alchemtech.playlistmaker.di

import android.media.MediaPlayer
import androidx.room.Room
import com.alchemtech.playlistmaker.data.converters.TrackDbConvertor
import com.alchemtech.playlistmaker.data.db.entity.AppDatabase
import com.alchemtech.playlistmaker.data.db.entity.TrackDao
import com.alchemtech.playlistmaker.data.db.favorite_list_repo.FavoriteTracksRepositoryImpl
import com.alchemtech.playlistmaker.data.repository.PlayerRepositoryImpl
import com.alchemtech.playlistmaker.domain.api.PlayerRepository
import com.alchemtech.playlistmaker.domain.db.FavoriteTracksRepository
import com.alchemtech.playlistmaker.domain.db.TracksDbInteractor
import com.alchemtech.playlistmaker.domain.impl.TracksDbInteractorImpl
import com.alchemtech.playlistmaker.domain.player.PlayerInteractor
import com.alchemtech.playlistmaker.domain.player.PlayerInteractorImlp
import com.alchemtech.playlistmaker.presentation.ui.player.model.PlayerViewModel
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val playerViewModel = module {
    viewModel<PlayerViewModel> {
        PlayerViewModel(
            player = get(),
            tracksDbInteractor = this.get<TracksDbInteractor>(),
            searchInteractor = get()
        )
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

    single<Gson> {
        Gson()
    }
    single<TracksDbInteractor> {
        TracksDbInteractorImpl(
            favoriteTracksRepository = get()
        )
    }
    single<FavoriteTracksRepository> {
        FavoriteTracksRepositoryImpl(
            tracksDao = get(),
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