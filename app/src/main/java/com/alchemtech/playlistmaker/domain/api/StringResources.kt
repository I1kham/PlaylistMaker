package com.alchemtech.playlistmaker.domain.api

import android.content.Context
import com.alchemtech.playlistmaker.data.resources.StringResourcesImpl

interface StringResources {
    fun getStringResources(id :Int) : String
    companion object  {
        fun provide(context: Context) : StringResources {
            return StringResourcesImpl(context)
        }
    }
}