package com.alchemtech.playlistmaker.data.cover_repository

import android.net.Uri

interface CoversRepository {
    suspend fun saveCover(uri: Uri): Uri
}