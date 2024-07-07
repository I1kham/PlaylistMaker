package com.alchemtech.playlistmaker.presentation.ui.main.model

import androidx.lifecycle.ViewModel
import com.alchemtech.playlistmaker.domain.Navigator

class MainViewModel(private val navigatorActivity: Navigator ) : ViewModel() {

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