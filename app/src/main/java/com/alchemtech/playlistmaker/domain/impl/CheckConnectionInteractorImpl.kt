package com.alchemtech.playlistmaker.domain.impl

import com.alchemtech.playlistmaker.domain.api.CheckConnectionInteractor
import com.alchemtech.playlistmaker.domain.api.CheckInternetConnectionRepository

class CheckConnectionInteractorImpl(val reopository : CheckInternetConnectionRepository): CheckConnectionInteractor  {
    override fun isChecked() : Boolean {
      return reopository.checkConnection()
    }
}