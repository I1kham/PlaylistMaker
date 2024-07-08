package com.alchemtech.playlistmaker.data.sharing

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.alchemtech.playlistmaker.domain.entity.emailData.EmailData
import com.alchemtech.playlistmaker.domain.sharing.ExternalNavigator

class ExternalNavigatorImpl(val context: Context) : ExternalNavigator {
    override fun shareLink(link: String) {
        Intent().apply {
            setFlags(FLAG_ACTIVITY_NEW_TASK)
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
            startActivity(context, this, null)
        }
    }

    override fun openLink(link: String) {

        startActivity(
            context,
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(link)
            ).setFlags(FLAG_ACTIVITY_NEW_TASK),
            null
        )
    }

    override fun openEmail(emailData: EmailData) {
        val buttonSupportIntent = Intent(Intent.ACTION_SENDTO)
        buttonSupportIntent.data = Uri.parse(emailData.toSupport)
        buttonSupportIntent.putExtra(
            Intent.EXTRA_EMAIL,
            emailData.emailAddresses.toTypedArray()
        )
        println(buttonSupportIntent)
        buttonSupportIntent.putExtra(
            Intent.EXTRA_TEXT,
            emailData.defMessage
        )
        buttonSupportIntent.putExtra(
            Intent.EXTRA_SUBJECT,
            emailData.defaultMessage
        )
        buttonSupportIntent.setFlags(FLAG_ACTIVITY_NEW_TASK)
        startActivity(context, buttonSupportIntent, null)
    }
}