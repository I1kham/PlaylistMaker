package com.alchemtech.playlistmaker.domain.api

import com.alchemtech.playlistmaker.domain.entity.Track

/*объект заготовка для Use case, для вызова метода поиска*/
interface TracksRepository {
    fun searchTracks(expression: String): List<Track>

}