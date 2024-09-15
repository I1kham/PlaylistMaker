package com.alchemtech.playlistmaker.data.cover_repository.tracks

import android.net.Uri

interface TracksCoversRepository {
    suspend fun saveCover(name: String, uri: Uri?): Uri?
}