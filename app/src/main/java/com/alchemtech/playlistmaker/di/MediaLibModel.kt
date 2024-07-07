package com.alchemtech.playlistmaker.di

import com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.model.MediaLibViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var mediaLibViewModel = module {
    viewModel<MediaLibViewModel>() {
        MediaLibViewModel()
    }
}