package com.alchemtech.playlistmaker.domain.sharing.impl

import com.alchemtech.playlistmaker.domain.entity.emailData.EmailData
import com.alchemtech.playlistmaker.domain.sharing.ExternalNavigator
import com.alchemtech.playlistmaker.domain.sharing.SharingInteractor

class SharingInteractorImpl(

    private val externalNavigator: ExternalNavigator,
    //private val stringResources: StringResources,
) : SharingInteractor {
    override fun shareApp(shareAppLink : String) {
        externalNavigator.shareLink(shareAppLink,
           // getShareAppLink()
        )
    }

    override fun openTerms(termsLink : String ) {
        externalNavigator.openLink(termsLink
         //   getTermsLink()
        )
    }

    override fun openSupport(emailData: EmailData) {
        externalNavigator.openEmail(emailData
            //getSupportEmailData()
        )
    }

//    private fun getShareAppLink(): String {
//        return stringResources.getStringResources(R.string.buttonShareApp)
//    }

//    private fun getSupportEmailData(): EmailData {
//        return EmailData()
//    }
// TODO:
//    private fun getTermsLink(): String {
//        return stringResources.getStringResources(R.string.linkTermsOfUse)
//    }
}