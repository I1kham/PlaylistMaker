package com.alchemtech.playlistmaker.track

import java.io.Serializable

data class Track(
    val trackName: String, // Название композиции
    val artistName: String, // Имя исполнителя;
    val trackTimeMillis: Long, // Продолжительность трека
    val artworkUrl100: String, // Ссылка на изображение обложки
    val trackId: String, //Id Track
    val collectionName : String?, //название альбома
    val releaseDate : String, // год релиза
    val primaryGenreName : String, //жанр трека
    val country : String // страна исполнителя
) : Serializable {
    fun getCoverArtwork() = artworkUrl100.replaceAfterLast('/',"512x512bb.jpg")
}
