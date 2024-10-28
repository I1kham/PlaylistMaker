package com.alchemtech.playlistmaker.data.cover_repository

interface CoversRepository {
    suspend fun saveCover(id: Long, uri: String?): String?
    suspend fun deleteCover(id: Long): Boolean
}