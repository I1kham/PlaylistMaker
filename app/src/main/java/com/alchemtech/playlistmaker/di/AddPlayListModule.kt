package com.alchemtech.playlistmaker.di

import com.alchemtech.playlistmaker.data.converters.PlayListDbConvertor
import com.alchemtech.playlistmaker.data.converters.TracksStringConvertor
import com.alchemtech.playlistmaker.data.db.entity.AppDatabase
import com.alchemtech.playlistmaker.data.db.entity.PlayListDao
import com.alchemtech.playlistmaker.data.db.play_lists_repo.PlayListsRepositoryImpl
import com.alchemtech.playlistmaker.domain.db.PlayListInteractor
import com.alchemtech.playlistmaker.domain.db.PlayListsRepository
import com.alchemtech.playlistmaker.domain.impl.PlayLIstInteractorImpl
import com.alchemtech.playlistmaker.presentation.ui.addPlayList.AddPlayListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var addPlayListViewModule = module {
    viewModel<AddPlayListViewModel> {
        AddPlayListViewModel(playListInteractor = get())
    }

    single<PlayListInteractor> {
        PlayLIstInteractorImpl(playListsRepository = get())
    }

    single<PlayListsRepository> {
        PlayListsRepositoryImpl(
            playListDao = get(),
            tracksStringConvertor = get(),
            playListDbConvertor = get()
        )
    }

    single<PlayListDao> {
        get<AppDatabase>().playListDao()
    }

    single<PlayListDbConvertor> {
        PlayListDbConvertor(tracksStringConvertor = get())
    }

    single<TracksStringConvertor> {
        TracksStringConvertor(gson = get())
    }
}