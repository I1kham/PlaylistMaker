package com.alchemtech.playlistmaker.presentation

import com.alchemtech.playlistmaker.data.NavigatorImpl
import com.alchemtech.playlistmaker.domain.Navigator
import com.alchemtech.playlistmaker.presentation.ui.main.model.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mainModule = module {
    single<MainViewModel> {
        MainViewModel(get())
    }
    single<Navigator> {
        NavigatorImpl(androidContext())
    }
}

