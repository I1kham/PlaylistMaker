package com.alchemtech.playlistmaker.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks_table")
data class TrackEntity(
    @PrimaryKey
    val trackId: String, //Id Track
    val trackName: String, // Название композиции
    val artistName: String, // Имя исполнителя;
    val trackTimeMillis: Long, // Продолжительность трека
    val artworkUrl100: String?, // Ссылка на изображение обложки
    val collectionName: String?, //название альбома
    val releaseDate: String?, // год релиза
    val primaryGenreName: String, //жанр трека
    val country: String, // страна исполнителя
    val previewUrl: String?, //случайные 30 сек трека
    var isFavorite: Boolean, //метка любимых
)