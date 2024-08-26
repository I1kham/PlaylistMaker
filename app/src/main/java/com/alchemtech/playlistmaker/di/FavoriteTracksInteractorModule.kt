package com.alchemtech.playlistmaker.di

import com.alchemtech.playlistmaker.data.db.favoriteListRepo.FavoriteTracksRepositoryImpl
import com.alchemtech.playlistmaker.domain.db.FavoriteTracksInteractor
import com.alchemtech.playlistmaker.domain.db.FavoriteTracksRepository
import com.alchemtech.playlistmaker.domain.impl.FavoriteTracksInteractorImpl
import org.koin.dsl.module

val favoriteTracksInteractorModule = module {
    single<FavoriteTracksInteractor> {
        FavoriteTracksInteractorImpl(
            favoriteTracksRepository = get()
        )
    }
}