package com.alchemtech.playlistmaker.data.cover_repository

import android.net.Uri

interface CoversRepository {
    suspend fun saveCover(name: String, uri: Uri?): Uri?
}