package com.alchemtech.playlistmaker.domain.sharing

import com.alchemtech.playlistmaker.domain.entity.emailData.EmailData

interface ExternalNavigator {
    fun shareLink(link: String)
    fun openLink(link: String)
    fun openEmail(emailData: EmailData)
}