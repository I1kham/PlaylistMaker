package com.alchemtech.playlistmaker.domain.sharing

import com.alchemtech.playlistmaker.domain.entity.emailData.EmailData

interface ExternalNavigator {
    fun shareLink(str: String)
    fun openLink(str: String)
    fun openEmail(emailData: EmailData)
}