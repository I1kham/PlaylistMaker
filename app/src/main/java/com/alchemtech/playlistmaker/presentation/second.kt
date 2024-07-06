package com.alchemtech.playlistmaker.presentation

import com.alchemtech.playlistmaker.data.resources.StringResourcesImpl
import com.alchemtech.playlistmaker.data.settings.impl.SettingsRepositoryImpl
import com.alchemtech.playlistmaker.data.sharing.ExternalNavigatorImpl
import com.alchemtech.playlistmaker.domain.api.StringResources
import com.alchemtech.playlistmaker.domain.settings.SettingsInteractor
import com.alchemtech.playlistmaker.domain.settings.SettingsRepository
import com.alchemtech.playlistmaker.domain.settings.impl.SettingsInteractorImpl
import com.alchemtech.playlistmaker.domain.sharing.ExternalNavigator
import com.alchemtech.playlistmaker.domain.sharing.SharingInteractor
import com.alchemtech.playlistmaker.domain.sharing.impl.SharingInteractorImpl
import com.alchemtech.playlistmaker.presentation.ui.settings.model.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val settingsModule = module {
    single<SettingsViewModel> {
        SettingsViewModel(get(), get(), get())
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