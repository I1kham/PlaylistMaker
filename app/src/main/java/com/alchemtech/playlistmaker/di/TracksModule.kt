package com.alchemtech.playlistmaker.di

import android.content.Context
import android.content.SharedPreferences
import com.alchemtech.playlistmaker.data.NavigatorImpl
import com.alchemtech.playlistmaker.data.network.NetworkClient
import com.alchemtech.playlistmaker.data.network.RetrofitNetworkClient
import com.alchemtech.playlistmaker.data.network.TrackApiService
import com.alchemtech.playlistmaker.data.repository.SharedHistoryRepositoryImpl
import com.alchemtech.playlistmaker.data.repository.TracksRepositoryImpl
import com.alchemtech.playlistmaker.domain.Navigator
import com.alchemtech.playlistmaker.domain.api.HistoryRepository
import com.alchemtech.playlistmaker.domain.api.SingleTrackInteractor
import com.alchemtech.playlistmaker.domain.api.TrackHistoryInteractor
import com.alchemtech.playlistmaker.domain.api.TracksInteractor
import com.alchemtech.playlistmaker.domain.api.TracksRepository
import com.alchemtech.playlistmaker.domain.impl.SingleTrackInteractorImpl
import com.alchemtech.playlistmaker.domain.impl.TrackHistoryInteractorImpl
import com.alchemtech.playlistmaker.domain.impl.TracksInteractorImpl
import com.alchemtech.playlistmaker.presentation.ui.tracks.model.TracksViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val searchingBaseUrl = "https://itunes.apple.com"
const val SAVED_TRACKS = "SAVED_TRACKS"
const val SAVED_LIST = "SAVED_LIST"

val tracksActivityViewModel = module {
    viewModel<TracksViewModel> {
        TracksViewModel(
            historyInteractor = get(),
            searchInteractor = get(),
            singleTrackInteractor = this.get(),
            navigatorActivity = get()
        )
    }
    single<Navigator> {
        NavigatorImpl(context = androidContext())
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
        TracksRepositoryImpl(networkClient = get())
    }

    single<NetworkClient> {
        RetrofitNetworkClient(connectService = get(), context = androidContext())
    }
    single<TrackApiService> {
        Retrofit.Builder()
            .baseUrl(searchingBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TrackApiService::class.java)
    }
}