package com.alchemtech.playlistmaker.creators

import com.alchemtech.playlistmaker.presentation.ui.tracks.TracksSearchPresenter

object TracksSearchPresenterCreator {

    fun provideTracksSearchPresenter(): TracksSearchPresenter {
        return TracksSearchPresenter()
    }

}