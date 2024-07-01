package com.alchemtech.playlistmaker.data.sharing.Impl

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.alchemtech.playlistmaker.data.dto.emailData.EmailData
import com.alchemtech.playlistmaker.data.sharing.ExternalNavigator

class ExternalNavigatorImpl(val context: Context) : ExternalNavigator {
    override fun shareLink(str: String) {
        Intent().apply {
            setFlags(FLAG_ACTIVITY_NEW_TASK)
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, str)
            type = "text/plain"
            startActivity(context, this, null)
        }
    }

    override fun openLink(str: String) {

        startActivity(
            context,
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(str)
            ).setFlags(FLAG_ACTIVITY_NEW_TASK),
            null
        )
    }

    override fun openEmail(emailData: EmailData) {
        val buttonSupportIntent = Intent(Intent.ACTION_SENDTO)
        buttonSupportIntent.data = Uri.parse(emailData.toSupport)
        buttonSupportIntent.putExtra(
            Intent.EXTRA_EMAIL,
            emailData.email
        )
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