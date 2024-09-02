package com.alchemtech.playlistmaker.data.converters

import com.alchemtech.playlistmaker.data.dto.trackDto.TrackDto
import com.alchemtech.playlistmaker.domain.entity.Track

class TrackDtoConvertor {

    fun map(track: TrackDto): Track {
        return Track(
            trackName = track.trackName,
            artistName = track.artistName,
            trackTimeMillis = track.trackTimeMillis,
            artworkUrl100 = track.artworkUrl100,
            trackId = track.trackId,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            previewUrl = track.previewUrl
        )
    }
}