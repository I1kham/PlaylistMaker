package com.alchemtech.playlistmaker.presentation.ui.settings.model

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.creators.ExternalCreator
import com.alchemtech.playlistmaker.creators.StringResourcesCreator
import com.alchemtech.playlistmaker.creators.ThemeInteractorCreator
import com.alchemtech.playlistmaker.domain.api.StringResources
import com.alchemtech.playlistmaker.domain.entity.emailData.EmailData
import com.alchemtech.playlistmaker.domain.settings.SettingsInteractor
import com.alchemtech.playlistmaker.domain.settings.model.ThemeSettings
import com.alchemtech.playlistmaker.domain.sharing.SharingInteractor

class SettingsViewModel(
    application: Application,
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor,
    private val stringResources: StringResources,
) : AndroidViewModel(application) {

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val context = this[APPLICATION_KEY] as Application
                SettingsViewModel(
                    context,
                    ExternalCreator.provideSharingInteractor(context),
                    ThemeInteractorCreator.provideThemeInteractor(context),
                    StringResourcesCreator.consume(context)
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        setDarkThemeState()
    }

    internal fun shareApp() {
        sharingInteractor.shareApp(stringResources.getStringResources(R.string.buttonShareApp))
    }

    internal fun openTermsOfUse() {
        sharingInteractor.openTerms(stringResources.getStringResources(R.string.linkTermsOfUse))
    }

    internal fun openSupport() {
        sharingInteractor.openSupport(
            EmailData(
                emailAddresses = listOf( stringResources.getStringResources(R.string.supportMail)),
                defMessage = stringResources.getStringResources(R.string.toSupportDefaultMail),
                defaultMessage = stringResources.getStringResources(R.string.toSupportMailSubject),
                toSupport = stringResources.getStringResources(R.string.toSupportUri)
            )
        )
    }

    private fun setDarkThemeState() {
        settingsInteractor.updateThemeSetting(
            ThemeSettings(
                AppCompatDelegate.getDefaultNightMode()
            )
        )
    }
}