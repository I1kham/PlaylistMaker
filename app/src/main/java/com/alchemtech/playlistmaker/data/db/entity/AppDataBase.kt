package com.alchemtech.playlistmaker.data.db.entity

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [TrackEntity::class])
abstract class AppDatabase : RoomDatabase(){
    companion object {
        const val NAME = "PlayList_Maker_DB"
    }
    abstract fun trackDao(): TrackDao
}
