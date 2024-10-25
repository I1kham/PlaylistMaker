package com.alchemtech.playlistmaker.di

import com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.model.FavoriteTracksViewModel
import com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.model.MediaLibViewModel
import com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.model.PlayListsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var mediaLibViewModel = module {
    viewModel<MediaLibViewModel> {
        MediaLibViewModel()
    }
}
var favoriteTracksViewModel = module {
    viewModel<FavoriteTracksViewModel> {
        FavoriteTracksViewModel(
            tracksDbInteractor = get(),
        )
    }
}

var playListsViewModel = module {
    viewModel<PlayListsViewModel>{
        PlayListsViewModel(playListInteractor = get())
    }
}