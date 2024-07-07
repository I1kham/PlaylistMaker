package com.alchemtech.playlistmaker.di

import com.alchemtech.playlistmaker.data.NavigatorImpl
import com.alchemtech.playlistmaker.domain.Navigator
import com.alchemtech.playlistmaker.presentation.ui.main.model.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mainActivityModule = module {
    factory<MainViewModel> {
        MainViewModel(navigatorActivity = this.get())
    }
    single<Navigator> {
        NavigatorImpl(androidContext())
    }
}