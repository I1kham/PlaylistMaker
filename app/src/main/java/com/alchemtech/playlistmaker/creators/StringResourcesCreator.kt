package com.alchemtech.playlistmaker.creators

import android.content.Context
import com.alchemtech.playlistmaker.data.resources.StringResourcesImpl
import com.alchemtech.playlistmaker.domain.api.StringResources

object StringResourcesCreator {

    fun consume(context: Context): StringResources {
        return StringResourcesImpl(context)
    }
}