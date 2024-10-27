package com.alchemtech.playlistmaker.data.sharing

import android.content.Context
import android.content.Intent
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.domain.db.PlayListInteractor
import com.alchemtech.playlistmaker.domain.entity.Track
import com.alchemtech.playlistmaker.domain.sharing.SharingPlayListRepository
import com.alchemtech.playlistmaker.presentation.ui.playerTimeFormatter

class SharingPlayListRepositoryImpl(
    val playListInteractor: PlayListInteractor,
    val context: Context,
) : SharingPlayListRepository {
    override suspend fun sharePlayList(playListId: Long) {
        val playList = playListInteractor.getPlayList(playListId)
        context.startActivity(
            Intent
                .createChooser(Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        createPlaylistInfoText(playList.name, playList.description, playList.tracks)
                    )
                    type = "text/plain"
                }, null)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

    private fun createPlaylistInfoText(
        name: String,
        description: String?,
        tracks: List<Track>,
    ): String {
        val tracksCountPlurals = context.resources.getQuantityString(
            R.plurals.plurals_tracks,
             tracks.size,
            tracks.size
        )
        val tracksCount = "${tracks.count()} $tracksCountPlurals"
        val tracksInfoStr = getTracksInfoStr(tracks)
            var ret = ""
            description?.let { ret = description }
        return "$name\n$ret\n$tracksCount\n$tracksInfoStr"
    }

    private fun getTracksInfoStr(tracks: List<Track>): String {
        return tracks.mapIndexed { index: Int, track: Track ->
            "${index + 1}. " +
                    "${track.artistName} - " +
                    "${track.trackName} " +
                    "(${track.trackTimeMillis.playerTimeFormatter()})\n"
        }.joinToString(separator = "")
    }
}