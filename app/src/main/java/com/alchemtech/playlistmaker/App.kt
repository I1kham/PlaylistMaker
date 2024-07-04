package com.alchemtech.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import com.alchemtech.playlistmaker.creators.ThemeInteractorCreator

class App : Application() {
  // private lateinit var stringResources : StringResources
    override fun onCreate() {
        super.onCreate()
       // stringResources =  StringResourcesCreator.consume(applicationContext)
        //StringResourcesCreator.setApplicationContext(this)
        switchTheme()
    }

    // TODO:
    private fun switchTheme() {
        setDefaultNightMode(
            ThemeInteractorCreator
                .provideThemeInteractor(this)
                .getThemeSettings().themeNumber
        )
    }
//   public fun getStringResources(stringResourceId :Int): String{
//        return stringResources.getStringResources(stringResourceId)
//    }
}