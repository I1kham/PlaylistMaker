package com.alchemtech.playlistmaker.di

import com.alchemtech.playlistmaker.data.sharing.SharingPlayListRepositoryImpl
import com.alchemtech.playlistmaker.domain.sharing.SharePlayListInteractor
import com.alchemtech.playlistmaker.domain.sharing.SharingPlayListRepository
import com.alchemtech.playlistmaker.domain.sharing.impl.SharePlayListInteractorImpl
import com.alchemtech.playlistmaker.presentation.ui.playList.PlayListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val playListViewModel = module {
    viewModel<PlayListViewModel> {
        PlayListViewModel(
            playListInteractor = get(),
            sharePlayListInteractor = get()
        )
    }

    single<SharePlayListInteractor> {
        SharePlayListInteractorImpl(sharingPlayListRepository = get())
    }

    single<SharingPlayListRepository> {
        SharingPlayListRepositoryImpl(
            playListInteractor = get(),
            context = androidContext()
        )
    }
}