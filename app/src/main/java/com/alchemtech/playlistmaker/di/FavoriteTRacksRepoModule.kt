package com.alchemtech.playlistmaker.di

import com.alchemtech.playlistmaker.data.db.favoriteListRepo.FavoriteTracksRepositoryImpl
import com.alchemtech.playlistmaker.domain.db.FavoriteTracksRepository
import org.koin.dsl.module

val favoriteTracksREpoModule = module {
    single<FavoriteTracksRepository> {
        FavoriteTracksRepositoryImpl(
            favoriteTRacksDao = get()
        )
    }
}