package com.alchemtech.playlistmaker.domain.api

interface StringResources {
    fun getStringResources(id :Int) : String
//    companion object  {
//        fun provide(context: Context) : StringResources {
//            return StringResourcesImpl(context)
//        }
//    }
}