package com.alchemtech.playlistmaker.domain.entity.emailData

data class EmailData (val email : String,
                 val defMessage: String,
                 val defaultMessage: String,
                 val toSupport : String){
    // TODO:
    //val resources = StringResourcesCreator.consume()

//    val email = arrayOf(resources.getStringResources(R.string.supportMail))
//    val defMessage: String = resources.getStringResources(R.string.toSupportDefaultMail)
//    val defaultMessage: String = resources.getStringResources(R.string.toSupportMailSubject)
//    val toSupport = resources.getStringResources(R.string.toSupportUri)
}
