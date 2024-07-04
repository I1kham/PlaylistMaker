package com.alchemtech.playlistmaker.creators

import android.content.Context
import com.alchemtech.playlistmaker.data.sharing.Impl.ExternalNavigatorImpl
import com.alchemtech.playlistmaker.domain.sharing.ExternalNavigator
import com.alchemtech.playlistmaker.domain.sharing.SharingInteractor
import com.alchemtech.playlistmaker.domain.sharing.impl.SharingInteractorImpl

object ExternalCreator {

    private fun provideExternalNavigator(context: Context): ExternalNavigator {
        return ExternalNavigatorImpl(context)
    }
// TODO:  
//    private fun provideStringResources(context: Context): StringResources {
//        return StringResources.provide(context)
//    }

    fun provideSharingInteractor(context: Context): SharingInteractor {
        return SharingInteractorImpl(
            provideExternalNavigator(context),
            //provideStringResources(context)
        )
    }
}