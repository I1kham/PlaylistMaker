package com.alchemtech.playlistmaker.di

import android.content.Context
import android.content.SharedPreferences
import com.alchemtech.playlistmaker.data.converters.TrackDtoConvertor
import com.alchemtech.playlistmaker.data.network.NetworkClient
import com.alchemtech.playlistmaker.data.network.RetrofitNetworkClient
import com.alchemtech.playlistmaker.data.network.TrackApiService
import com.alchemtech.playlistmaker.data.repository.SharedHistoryRepositoryImpl
import com.alchemtech.playlistmaker.data.repository.TracksRepositoryImpl
import com.alchemtech.playlistmaker.domain.api.HistoryRepository
import com.alchemtech.playlistmaker.domain.api.SingleTrackInteractor
import com.alchemtech.playlistmaker.domain.api.TrackHistoryInteractor
import com.alchemtech.playlistmaker.domain.api.TracksInteractor
import com.alchemtech.playlistmaker.domain.api.TracksRepository
import com.alchemtech.playlistmaker.domain.impl.SingleTrackInteractorImpl
import com.alchemtech.playlistmaker.domain.impl.TrackHistoryInteractorImpl
import com.alchemtech.playlistmaker.domain.impl.TracksInteractorImpl
import com.alchemtech.playlistmaker.presentation.ui.tracks.model.TracksFragmentModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
private const val SEARCH_BASE_URL = "https://itunes.apple.com"
private const val SAVED_TRACKS = "SAVED_TRACKS"
private const val SAVED_LIST = "SAVED_LIST"
val tracksActivityViewModel = module {
    viewModel<TracksFragmentModel> {
        TracksFragmentModel(
            historyInteractor = get(),
            searchInteractor = get(),
            singleTrackInteractor = this.get(),
        )
    }
    single<SingleTrackInteractor> {
        SingleTrackInteractorImpl(historyRepository = this.get())
    }

    factory <TrackHistoryInteractor> {
        TrackHistoryInteractorImpl(historyRepository = get())
    }

    factory<HistoryRepository> {
        SharedHistoryRepositoryImpl(
            SAVED_LIST,
            sharedPreferences = this.get (),
            gson = get()
        )
    }
    factory<SharedPreferences> {
        androidContext().getSharedPreferences(
            SAVED_TRACKS, Context.MODE_PRIVATE
        )
    }

    single<TracksInteractor> {
        TracksInteractorImpl(tracksRepository = get())
    }

    single<TracksRepository> {
        TracksRepositoryImpl(
            networkClient = get(),
            trackDao = get(),
            trackDtoConvertor = get()
        )
    }

    single<NetworkClient> {
        RetrofitNetworkClient(connectService = get(), context = androidContext())
    }
    single<TrackApiService> {
        Retrofit.Builder()
            .baseUrl(SEARCH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TrackApiService::class.java)
    }

    single<TrackDtoConvertor> {
        TrackDtoConvertor()
    }
}