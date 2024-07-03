package com.alchemtech.playlistmaker.domain.api

import com.alchemtech.playlistmaker.data.resources.StringResourcesImpl

interface StringResources {
    fun getStringResources(id :Int) : String
    companion object  {
        fun provide() : StringResources {
            return StringResourcesImpl()
        }
    }
}