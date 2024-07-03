package com.alchemtech.playlistmaker.domain.sharing.impl

import android.content.Context
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.domain.api.StringResources
import com.alchemtech.playlistmaker.domain.entity.emailData.EmailData
import com.alchemtech.playlistmaker.domain.sharing.ExternalNavigator
import com.alchemtech.playlistmaker.domain.sharing.SharingInteractor

class SharingInteractorImpl(

    private val externalNavigator: ExternalNavigator,
    private val stringResources: StringResources,
) : SharingInteractor {
    override fun shareApp() {
        externalNavigator.shareLink(getShareAppLink())
    }

    override fun openTerms() {
        externalNavigator.openLink(getTermsLink())
    }

    override fun openSupport() {
        externalNavigator.openEmail(getSupportEmailData())
    }

    private fun getShareAppLink(): String {
        return stringResources.getStringResources(R.string.buttonShareApp)
    }

    private fun getSupportEmailData(): EmailData {
        return EmailData()
    }

    private fun getTermsLink(): String {
        println(R.string.linkTermsOfUse)
        return stringResources.getStringResources(R.string.linkTermsOfUse)
    }
}