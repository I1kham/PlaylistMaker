package com.alchemtech.playlistmaker.domain.sharing.impl

import com.alchemtech.playlistmaker.domain.sharing.SharePlayListInteractor
import com.alchemtech.playlistmaker.domain.sharing.SharingPlayListRepository

class SharePlayListInteractorImpl(val sharingPlayListRepository: SharingPlayListRepository):SharePlayListInteractor {

    override suspend fun sharePlayList(playListId: Long) {
       sharingPlayListRepository.sharePlayList(playListId)
    }
}