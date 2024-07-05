package com.alchemtech.playlistmaker.data.resources

import android.content.Context
import androidx.core.content.ContextCompat.getString
import com.alchemtech.playlistmaker.domain.api.StringResources

class StringResourcesImpl(val context: Context) : StringResources {
    override fun getStringResources(id : Int) : String {
        return getString(context, id)
    }

}