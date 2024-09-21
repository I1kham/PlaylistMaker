package com.alchemtech.playlistmaker.di

import com.alchemtech.playlistmaker.presentation.ui.playList.PlayListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val playListViewModel = module {
    viewModel<PlayListViewModel> {
        PlayListViewModel(playListInteractor = get())
    }
}