package com.alchemtech.playlistmaker.domain.models

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

data class Track(
    val trackName: String, // Название композиции
    val artistName: String, // Имя исполнителя;
    val trackTimeMillis: Long, // Продолжительность трека
    val artworkUrl100: String, // Ссылка на изображение обложки
    val trackId: String, //Id Track
    val collectionName: String?, //название альбома
    val releaseDate: String, // год релиза
    val primaryGenreName: String, //жанр трека
    val country: String, // страна исполнителя
    val previewUrl: String, //случайные 30 сек трека
    //  val artworkUrl512: String // ссыдка на большое изображение обложки
) : Serializable {


    fun getArtworkUrl512(): Any {
        return artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")

    }

    fun getTimeString(): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTimeMillis)
    }

    fun getReleaseDateString(): String {
        return releaseDate.substring(0 until 4) + " год"
    }
}

