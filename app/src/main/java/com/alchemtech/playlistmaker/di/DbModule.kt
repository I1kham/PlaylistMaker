package com.alchemtech.playlistmaker.di

import androidx.room.Room
import com.alchemtech.playlistmaker.data.db.entity.AppDatabase
import com.alchemtech.playlistmaker.data.db.entity.TrackDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    single<AppDatabase> {
        Room
            .databaseBuilder(androidContext(), AppDatabase::class.java, AppDatabase.NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
    single<TrackDao> { get<AppDatabase>().trackDao() }
}