package com.alchemtech.playlistmaker.di

import com.alchemtech.playlistmaker.presentation.ui.addPlayList.AddPlayListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var addPlayListViewModule = module {
    viewModel<AddPlayListViewModel> {
        AddPlayListViewModel()
    }
}