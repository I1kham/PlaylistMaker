package com.alchemtech.playlistmaker.di

import com.alchemtech.playlistmaker.presentation.ui.playList.fragments.model.TracksRecycleFragmentModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tracksRecycleFragmentModel = module {
    viewModel<TracksRecycleFragmentModel> {
        TracksRecycleFragmentModel(playListInteractor = get())
    }
}