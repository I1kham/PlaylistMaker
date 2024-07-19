package com.alchemtech.playlistmaker.di

import com.alchemtech.playlistmaker.presentation.ui.main.model.StartViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val startViewModel = module{
       viewModel<StartViewModel> {
           StartViewModel()
       }
   }
