package com.alchemtech.playlistmaker.di

import com.alchemtech.playlistmaker.presentation.ui.player.model.AddTrackToPlayListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var addTrackToPlayListModule = module {
    viewModel<AddTrackToPlayListViewModel> {
        AddTrackToPlayListViewModel(
            playListInteractor = get(),
            tracksDbInteractor = get()
        )
    }
}