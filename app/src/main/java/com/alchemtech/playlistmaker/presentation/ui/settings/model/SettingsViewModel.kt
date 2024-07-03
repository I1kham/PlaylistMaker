package com.alchemtech.playlistmaker.presentation.ui.settings.model

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alchemtech.playlistmaker.creators.ExternalCreator
import com.alchemtech.playlistmaker.creators.ThemeInteractorCreator
import com.alchemtech.playlistmaker.domain.settings.SettingsInteractor
import com.alchemtech.playlistmaker.domain.settings.model.ThemeSettings
import com.alchemtech.playlistmaker.domain.sharing.SharingInteractor

class SettingsViewModel(
    application: Application,
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor,

) : AndroidViewModel(application) {

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val context = this[APPLICATION_KEY] as Application
                SettingsViewModel(
                    context,
                    ExternalCreator.provideSharingInteractor(context),
                    ThemeInteractorCreator.provideThemeInteractor(context),
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
       setDarkThemeState()
    }

    internal fun shareApp() {
        sharingInteractor.shareApp()
    }

    internal fun openTermsOfUse() {
        sharingInteractor.openTerms()
    }

    internal fun openSupport() {
        sharingInteractor.openSupport()
    }

    private fun setDarkThemeState() {
        settingsInteractor.updateThemeSetting(
            ThemeSettings(
                AppCompatDelegate.getDefaultNightMode()
            )
        )
    }
}