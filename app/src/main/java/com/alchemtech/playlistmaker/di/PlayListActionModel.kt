package com.alchemtech.playlistmaker.di

import com.alchemtech.playlistmaker.presentation.ui.playList.fragments.model.PlayListActionFragmentModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var playListActionModule = module {
    viewModel<PlayListActionFragmentModel> {
        PlayListActionFragmentModel(playListInteractor = get())
    }
}