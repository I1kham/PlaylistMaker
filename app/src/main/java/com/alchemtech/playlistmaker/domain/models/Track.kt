package com.alchemtech.playlistmaker.domain.models

import java.io.Serializable

data class Track(
    val trackName: String, // Название композиции
    val artistName: String, // Имя исполнителя;
    val trackTimeMillis: String, // Продолжительность трека
    val artworkUrl100: String, // Ссылка на изображение обложки
    val trackId: String, //Id Track
    val collectionName: String?, //название альбома
    val releaseDate: String, // год релиза
    val primaryGenreName: String, //жанр трека
    val country: String, // страна исполнителя
    val previewUrl: String, //случайные 30 сек трека
    val artworkUrl512: String // ссыдка на большое изображение обложки
) : Serializable
