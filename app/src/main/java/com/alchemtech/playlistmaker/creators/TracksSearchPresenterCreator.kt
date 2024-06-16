package com.alchemtech.playlistmaker.creators

import com.alchemtech.playlistmaker.presentation.ui.tracks.TracksSearchPresenter
import com.alchemtech.playlistmaker.presentation.ui.tracks.TracksView

object TracksSearchPresenterCreator {

    fun provideTracksSearchPresenter(
        view: TracksView,
    ): TracksSearchPresenter {
        return TracksSearchPresenter(view)
    }

}