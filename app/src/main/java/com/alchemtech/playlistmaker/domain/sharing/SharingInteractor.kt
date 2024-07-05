package com.alchemtech.playlistmaker.domain.sharing

import com.alchemtech.playlistmaker.domain.entity.emailData.EmailData

interface SharingInteractor {
    fun shareApp(shareAppLink: String)
    fun openTerms(termsLink: String)
    fun openSupport(emailData: EmailData)
}