package com.alchemtech.playlistmaker.domain.sharing.impl

import android.content.Context
import androidx.core.content.ContextCompat.getString
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.data.dto.emailData.EmailData
import com.alchemtech.playlistmaker.data.sharing.ExternalNavigator
import com.alchemtech.playlistmaker.domain.sharing.SharingInteractor

class SharingInteractorImpl(
    private val context: Context,
    private val externalNavigator: ExternalNavigator,
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
        return getString(context, R.string.buttonShareApp)
    }

    private fun getSupportEmailData(): EmailData {
        return EmailData(context)
    }

    private fun getTermsLink(): String {
        return context.getString(R.string.linkTermsOfUse)
    }
}