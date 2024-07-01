package com.alchemtech.playlistmaker.presentation.ui.settings.model

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alchemtech.playlistmaker.creators.ExternalCreator
import com.alchemtech.playlistmaker.creators.SettingsInteractorCreator
import com.alchemtech.playlistmaker.domain.settings.SettingsInteractor
import com.alchemtech.playlistmaker.domain.settings.model.ThemeSettings
import com.alchemtech.playlistmaker.domain.sharing.SharingInteractor

class SettingsViewModel(
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor,

) : ViewModel() {

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingsViewModel(
                    ExternalCreator.provideSharingInteractor(),
                    SettingsInteractorCreator.provideSettingsInteractor(),
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