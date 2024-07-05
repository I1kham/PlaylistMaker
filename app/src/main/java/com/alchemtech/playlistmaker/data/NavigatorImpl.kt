package com.alchemtech.playlistmaker.data

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import com.alchemtech.playlistmaker.domain.Navigator
import com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.MediaLibActivity
import com.alchemtech.playlistmaker.presentation.ui.player.PlayerActivity
import com.alchemtech.playlistmaker.presentation.ui.settings.SettingsActivity
import com.alchemtech.playlistmaker.presentation.ui.tracks.TracksActivity

class NavigatorImpl(val context: Context) : Navigator {
    override fun toPlayer() {
        val playerIntent = Intent(context, PlayerActivity::class.java).apply {}
        playerIntent.setFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(playerIntent)
    }

    override fun toSearch() {
        val toSearch = Intent(
            context,
            TracksActivity::class.java
        )
        toSearch.setFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(toSearch)
    }

    override fun toMediaLib() {
        val toMediaLib = Intent(
            context,
            MediaLibActivity::class.java
        )
        toMediaLib.setFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(toMediaLib)
    }

    override fun toSettings() {
        val toSettings = Intent(context, SettingsActivity::class.java)
        toSettings.setFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(toSettings)
    }
}
