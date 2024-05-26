package com.alchemtech.playlistmaker.data.dto.response

import com.alchemtech.playlistmaker.data.dto.trackDto.TrackDto

class TracksSearchResponse(
    val searchType: String,
    val expression: String,
    val results: List<TrackDto>,
) : Response()