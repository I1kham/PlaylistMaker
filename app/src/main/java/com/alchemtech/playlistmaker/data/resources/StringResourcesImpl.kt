package com.alchemtech.playlistmaker.data.resources

import android.content.res.Resources
import com.alchemtech.playlistmaker.domain.api.StringResources

class StringResourcesImpl : StringResources {
    override fun getStringResources(id : Int) : String {
        return Resources.getSystem().getString(id)
    }

}