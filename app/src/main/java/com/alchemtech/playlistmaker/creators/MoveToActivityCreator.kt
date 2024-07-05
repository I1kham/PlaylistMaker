package com.alchemtech.playlistmaker.creators

import android.content.Context
import com.alchemtech.playlistmaker.data.NavigatorImpl
import com.alchemtech.playlistmaker.domain.Navigator

object MoveToActivityCreator {

    fun provideMoveToActivity(context: Context): Navigator {
        return NavigatorImpl(context)
    }
}