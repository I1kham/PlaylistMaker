package com.alchemtech.playlistmaker.domain.sharing

interface SharePlayListInteractor {
    suspend  fun  sharePlayList(playListId: Long)
}