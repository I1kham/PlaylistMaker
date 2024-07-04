package com.alchemtech.playlistmaker.creators

import android.content.Context
import com.alchemtech.playlistmaker.data.resources.StringResourcesImpl
import com.alchemtech.playlistmaker.domain.api.StringResources

object StringResourcesCreator {
//    private lateinit var applicationContext: Application
//    fun setApplicationContext(application: Application) {
//        applicationContext = application
//    }
// TODO:
//    fun consume(): StringResources {
//        return StringResources.provide(
//            applicationContext
//        )
//    }

        fun consume(context: Context) : StringResources {
            return StringResourcesImpl(context)
        }
}