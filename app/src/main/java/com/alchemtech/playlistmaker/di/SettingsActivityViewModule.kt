package com.alchemtech.playlistmaker.di

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
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

const val THEME = "THEME"
val settingsActivityModule = module {
    factory<SettingsViewModel> {
        SettingsViewModel(
            sharingInteractor = this.get(),
            settingsInteractor = this.get(),
            stringResources = this.get()
        )
    }
    single<SharingInteractor> {
        SharingInteractorImpl(externalNavigator = get())
    }
    single<SettingsInteractor> {
        SettingsInteractorImpl(settingsRepository = get())
    }
    single<StringResources> {
        StringResourcesImpl(context = androidContext())
    }
    single<SettingsRepository> {
        SettingsRepositoryImpl(
            key = THEME,
            sharedPreferences = this.get()
        )
    }
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            THEME,
            MODE_PRIVATE
        )
    }
    single<ExternalNavigator> {
        ExternalNavigatorImpl(androidContext())
    }
}