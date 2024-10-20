package com.alchemtech.playlistmaker.data.cover_repository

import android.net.Uri

interface CoversRepository {
    suspend fun saveCover(id: Long, uri: Uri?): Uri?
    suspend fun deleteCover(id: Long?): Boolean
}