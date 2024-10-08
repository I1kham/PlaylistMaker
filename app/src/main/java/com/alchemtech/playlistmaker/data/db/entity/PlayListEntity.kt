package com.alchemtech.playlistmaker.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "play_lists_table")
data class PlayListEntity(
    @PrimaryKey(autoGenerate = true)
    var playListId: Long,
    val name: String,
    val description: String?,
    val coverUri: String?,
    val tracks: String?)