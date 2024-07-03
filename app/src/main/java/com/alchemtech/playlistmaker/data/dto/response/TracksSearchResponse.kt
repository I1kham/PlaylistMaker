package com.alchemtech.playlistmaker.data.dto.response

import com.alchemtech.playlistmaker.data.dto.trackDto.TrackDto

class TracksSearchResponse(
    val results: List<TrackDto>,
) : Response()