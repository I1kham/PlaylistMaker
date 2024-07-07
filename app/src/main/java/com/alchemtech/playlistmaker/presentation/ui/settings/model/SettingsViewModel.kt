package com.alchemtech.playlistmaker.presentation.ui.settings.model

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.domain.api.StringResources
import com.alchemtech.playlistmaker.domain.entity.emailData.EmailData
import com.alchemtech.playlistmaker.domain.settings.SettingsInteractor
import com.alchemtech.playlistmaker.domain.settings.model.ThemeSettings
import com.alchemtech.playlistmaker.domain.sharing.SharingInteractor

class SettingsViewModel(
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor,
    private val stringResources: StringResources,
) : ViewModel() {

    internal fun shareApp() {
        sharingInteractor.shareApp(stringResources.getStringResources(R.string.buttonShareApp))
    }

    internal fun openTermsOfUse() {
        sharingInteractor.openTerms(stringResources.getStringResources(R.string.linkTermsOfUse))
    }

    internal fun openSupport() {
        sharingInteractor.openSupport(
            EmailData(
                emailAddresses = listOf(stringResources.getStringResources(R.string.supportMail)),
                defMessage = stringResources.getStringResources(R.string.toSupportDefaultMail),
                defaultMessage = stringResources.getStringResources(R.string.toSupportMailSubject),
                toSupport = stringResources.getStringResources(R.string.toSupportUri)
            )
        )
    }

    internal fun setDarkThemeState() {
        settingsInteractor.updateThemeSetting(
            ThemeSettings(
                AppCompatDelegate.getDefaultNightMode()
            )
        )
    }
}