package com.alchemtech.playlistmaker.data.cover_repository.tracks

import android.net.Uri

interface TracksCoversRepository {
    suspend fun saveCover(id: Long, uri: Uri?): Uri?
}