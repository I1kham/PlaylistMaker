package com.alchemtech.playlistmaker.presentation.ui.main.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alchemtech.playlistmaker.creators.MoveToActivityCreator
import com.alchemtech.playlistmaker.domain.MoveTo

class MainViewModel(private val moveToActivity: MoveTo ) : ViewModel() {
    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MainViewModel(
                    MoveToActivityCreator.provideMoveToActivity()
                )
            }
        }
    }

    internal fun toSearch() {
        moveToActivity.toSearch()
    }

    internal fun toMediaLib() {
        moveToActivity.toMediaLib()
    }

    internal fun toSettings() {
        moveToActivity.toSettings()
    }

}