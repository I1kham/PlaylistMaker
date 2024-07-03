package com.alchemtech.playlistmaker.domain.entity.emailData

import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.creators.StringResourcesCreator

class EmailData {
    val resources = StringResourcesCreator.consume()

    val email = arrayOf(resources.getStringResources(R.string.supportMail))
    val defMessage: String = resources.getStringResources(R.string.toSupportDefaultMail)
    val defaultMessage: String = resources.getStringResources(R.string.toSupportMailSubject)
    val toSupport = resources.getStringResources(R.string.toSupportUri)
}
