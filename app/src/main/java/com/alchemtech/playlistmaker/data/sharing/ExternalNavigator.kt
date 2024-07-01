package com.alchemtech.playlistmaker.data.sharing

import com.alchemtech.playlistmaker.data.dto.emailData.EmailData

interface ExternalNavigator {
    fun shareLink(str: String)
    fun openLink(str: String)
    fun openEmail(emailData: EmailData)
}