package com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.model

import androidx.lifecycle.ViewModel
import com.alchemtech.playlistmaker.domain.db.PlayListInteractor
import com.alchemtech.playlistmaker.domain.entity.PlayList

class PlayListsViewModel(
    private val playListInteractor: PlayListInteractor,
) : ViewModel() {
    var playLists: List<PlayList> = listOf(
        PlayList(
            "Play1",
            "Description",
            "content://media/picker/0/com.android.providers.media.photopicker/media/1000000034",
        )
    )
}