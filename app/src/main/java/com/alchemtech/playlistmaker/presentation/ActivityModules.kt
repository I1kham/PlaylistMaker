package com.alchemtech.playlistmaker.presentation

import com.alchemtech.playlistmaker.data.NavigatorImpl
import com.alchemtech.playlistmaker.data.network.NetworkClient
import com.alchemtech.playlistmaker.data.network.RetrofitNetworkClient
import com.alchemtech.playlistmaker.data.repository.PlayerRepositoryImpl
import com.alchemtech.playlistmaker.data.repository.SharedHistoryRepositoryImpl
import com.alchemtech.playlistmaker.data.repository.TracksRepositoryImpl
import com.alchemtech.playlistmaker.data.resources.StringResourcesImpl
import com.alchemtech.playlistmaker.data.settings.impl.SettingsRepositoryImpl
import com.alchemtech.playlistmaker.data.sharing.ExternalNavigatorImpl
import com.alchemtech.playlistmaker.domain.Navigator
import com.alchemtech.playlistmaker.domain.api.HistoryRepository
import com.alchemtech.playlistmaker.domain.api.PlayerRepository
import com.alchemtech.playlistmaker.domain.api.SingleTrackInteractor
import com.alchemtech.playlistmaker.domain.api.StringResources
import com.alchemtech.playlistmaker.domain.api.TrackHistoryInteractor
import com.alchemtech.playlistmaker.domain.api.TracksInteractor
import com.alchemtech.playlistmaker.domain.api.TracksRepository
import com.alchemtech.playlistmaker.domain.impl.SingleTrackInteractorImpl
import com.alchemtech.playlistmaker.domain.impl.TrackHistoryInteractorImpl
import com.alchemtech.playlistmaker.domain.impl.TracksInteractorImpl
import com.alchemtech.playlistmaker.domain.player.PlayerInteractor
import com.alchemtech.playlistmaker.domain.player.PlayerInteractorImlp
import com.alchemtech.playlistmaker.domain.settings.SettingsInteractor
import com.alchemtech.playlistmaker.domain.settings.SettingsRepository
import com.alchemtech.playlistmaker.domain.settings.impl.SettingsInteractorImpl
import com.alchemtech.playlistmaker.domain.sharing.ExternalNavigator
import com.alchemtech.playlistmaker.domain.sharing.SharingInteractor
import com.alchemtech.playlistmaker.domain.sharing.impl.SharingInteractorImpl
import com.alchemtech.playlistmaker.presentation.ui.main.model.MainViewModel
import com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.model.MediaLibViewModel
import com.alchemtech.playlistmaker.presentation.ui.player.model.PlayerViewModel
import com.alchemtech.playlistmaker.presentation.ui.settings.model.SettingsViewModel
import com.alchemtech.playlistmaker.presentation.ui.tracks.model.TracksViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mainActivityModule = module {
    factory<MainViewModel> {
        MainViewModel(navigatorActivity = get())
    }
    single<Navigator> {
        NavigatorImpl(androidContext())
    }
}

val settingsActivityModule = module {
    factory<SettingsViewModel> {
        SettingsViewModel(
            sharingInteractor = get(),
            settingsInteractor = get(),
            stringResources = get()
        )
    }
    single<SharingInteractor> {
        SharingInteractorImpl(get())
    }

    single<SettingsInteractor> {
        SettingsInteractorImpl(get())

    }

    single<StringResources> {
        StringResourcesImpl(androidContext())

    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(androidContext())

    }

    single<ExternalNavigator> {
        ExternalNavigatorImpl(androidContext())
    }
}
val tracksActivityViewModel = module {
    factory<TracksViewModel> {
        TracksViewModel(
            historyInteractor = get(),
            searchInteractor = get(),
            singleTrackInteractor = get(),
            navigatorActivity = get()
        )
    }
    single<Navigator> {
        NavigatorImpl(context = androidContext())
    }

    single<TrackHistoryInteractor> {
        TrackHistoryInteractorImpl(repository = get())
    }

    single<HistoryRepository> {
        SharedHistoryRepositoryImpl(context = androidContext())
    }

    single<TracksInteractor> {
        TracksInteractorImpl(repository = get())
    }

    single<TracksRepository> {
        TracksRepositoryImpl(networkClient = get())
    }

    single<NetworkClient> {
        RetrofitNetworkClient(context = androidContext())
    }
}

val playerViewModel = module {
    factory<PlayerViewModel> {
        PlayerViewModel(
            singleTrackRepository = get(),
            player = get()
        )
    }
    single<SingleTrackInteractor> {
        SingleTrackInteractorImpl(repository = get())
    }
    single<PlayerInteractor> {
        PlayerInteractorImlp(playerRepository = get())
    }
    single<PlayerRepository> {
        PlayerRepositoryImpl()
    }
}
var mediaLibViewModel = module {
    single {
        MediaLibViewModel()
    }
}
