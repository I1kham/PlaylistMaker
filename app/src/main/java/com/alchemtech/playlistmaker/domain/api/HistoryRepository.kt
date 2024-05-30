package com.alchemtech.playlistmaker.domain.api

import android.content.Context
import com.alchemtech.playlistmaker.data.dto.trackDto.TrackDto
import java.io.Serializable

interface HistoryRepository { // TODO: DbRepository
    fun getSavedPref(): List<TrackDto>?
    fun setSavedPref( objects: Serializable)

    fun setValues(name: String, key: String, context: Context)
}