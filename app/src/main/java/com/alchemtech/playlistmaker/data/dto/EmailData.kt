package com.alchemtech.playlistmaker.data.dto

import android.content.Context
import androidx.core.content.ContextCompat.getString
import com.alchemtech.playlistmaker.R

class EmailData(context: Context) {
    val email = arrayOf(getString(context, R.string.supportMail))
    val defMessage: String = context.getString(R.string.toSupportDefaultMail)
    val defaultMessage: String = context.getString(R.string.toSupportMailSubject)
    val toSupport = context.getString(R.string.toSupportUri)
}
