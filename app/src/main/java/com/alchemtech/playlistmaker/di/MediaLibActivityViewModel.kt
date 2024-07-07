package com.alchemtech.playlistmaker.di

import com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.model.MediaLibViewModel
import org.koin.dsl.module

var mediaLibViewModel = module {
    single {
        MediaLibViewModel()
    }
}