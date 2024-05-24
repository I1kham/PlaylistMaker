package com.alchemtech.playlistmaker.data.dto.response

import com.alchemtech.playlistmaker.data.dto.response.Response
import com.alchemtech.playlistmaker.data.dto.trackDto.TrackDto

/*todo расширенный класс для хранения ответа от сервера*/
class TracksSearchResponse(
    val searchType: String,
    val expression: String,
    val results: List<TrackDto>,
) : Response()