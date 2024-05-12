package com.alchemtech.playlistmaker.data.dto

class TracksSearchResponse(
    val searchType: String,
    val expression: String,
    val results: List<TrackDto>,
) : Response()