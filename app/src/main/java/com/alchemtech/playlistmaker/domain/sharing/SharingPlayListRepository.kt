package com.alchemtech.playlistmaker.domain.sharing

interface SharingPlayListRepository {
   suspend fun sharePlayList(playListId: Long)
}