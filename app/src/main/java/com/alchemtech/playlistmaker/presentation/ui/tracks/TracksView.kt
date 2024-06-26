package com.alchemtech.playlistmaker.presentation.ui.tracks

import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.presentation.ui.tracks.model.TracksActivityState
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface TracksView  : MvpView {


    @StateStrategyType(OneExecutionStateStrategy::class)
    fun stopView()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun navigateTRackToPlayer(track: Track)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun render(state: TracksActivityState){
        println("51651651651651651651651")
    }
}