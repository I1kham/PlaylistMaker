package com.alchemtech.playlistmaker.domain.sharing.impl

import com.alchemtech.playlistmaker.domain.entity.emailData.EmailData
import com.alchemtech.playlistmaker.domain.sharing.ExternalNavigator
import com.alchemtech.playlistmaker.domain.sharing.SharingInteractor

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigator,
) : SharingInteractor {
    override fun shareApp(shareAppLink: String) {
        externalNavigator.shareLink(shareAppLink)
    }

    override fun openTerms(termsLink: String) {
        externalNavigator.openLink(
            termsLink
        )
    }

    override fun openSupport(emailData: EmailData) {
        externalNavigator.openEmail(
            emailData
        )
    }

}