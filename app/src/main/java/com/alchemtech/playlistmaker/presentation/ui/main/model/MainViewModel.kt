package com.alchemtech.playlistmaker.presentation.ui.main.model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alchemtech.playlistmaker.creators.MoveToActivityCreator
import com.alchemtech.playlistmaker.domain.Navigator

class MainViewModel(private val navigatorActivity: Navigator ) : ViewModel() {
    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val context = this[APPLICATION_KEY] as Application
                MainViewModel(
                    MoveToActivityCreator.provideMoveToActivity(context)
                )
            }
        }
    }

    internal fun toSearch() {
        navigatorActivity.toSearch()
    }

    internal fun toMediaLib() {
        navigatorActivity.toMediaLib()
    }

    internal fun toSettings() {
        navigatorActivity.toSettings()
    }

}