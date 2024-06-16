package com.alchemtech.playlistmaker.presentation.ui.tracks

import com.alchemtech.playlistmaker.domain.entity.Track

interface TracksView {

    fun showHistoryListButTitle(visibility: Boolean)
//disableHistoryList()
    fun showNoDataErr(visibility: Boolean)
//setAllErrLayoutsGONE
    fun showNoConnection(visibility: Boolean)
//setAllErrLayoutsGONE
    fun showProgressBar(visibility: Boolean)

    fun showTrackRecycle(visibility: Boolean)


    fun hideKeyBoard()

    fun upDateRecycle()
    fun clearInputText()
    fun stopView()
    fun navigateTRackToPlayer(track: Track )
}