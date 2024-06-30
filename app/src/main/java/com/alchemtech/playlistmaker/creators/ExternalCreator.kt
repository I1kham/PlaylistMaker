package com.alchemtech.playlistmaker.creators

import android.app.Application
import com.alchemtech.playlistmaker.data.sharing.ExternalNavigator
import com.alchemtech.playlistmaker.data.sharing.Impl.ExternalNavigatorImpl
import com.alchemtech.playlistmaker.domain.sharing.SharingInteractor
import com.alchemtech.playlistmaker.domain.sharing.impl.SharingInteractorImpl

object ExternalCreator {
    private lateinit var applicationContext: Application
    fun setApplicationContext(application: Application) {
        applicationContext = application
    }
   private fun provideExternalNavigator() : ExternalNavigator{
        return ExternalNavigatorImpl(applicationContext)
    }

    fun provideSharingInteractor() : SharingInteractor {
        return SharingInteractorImpl(applicationContext,provideExternalNavigator())
    }
}