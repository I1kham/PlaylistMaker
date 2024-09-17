package com.alchemtech.playlistmaker.domain.entity

data class Track(
    val trackName: String, // Название композиции
    val artistName: String, // Имя исполнителя;
    val trackTimeMillis: Long, // Продолжительность трека
    val artworkUrl100: String?, // Ссылка на изображение обложки
    val trackId: String, //Id Track
    val collectionName: String?, //название альбома
    val releaseDate: String?, // год релиза
    val primaryGenreName: String, //жанр трека
    val country: String, // страна исполнителя
    val previewUrl: String?, //случайные 30 сек трека
    var isFavorite: Boolean = false, //метка любимых
)
{
    override fun equals(other: Any?): Boolean {
        return if (other is Track) {
            (trackId == other.trackId)
        } else {
            super.equals(other)
        }
    }

    override fun hashCode(): Int {
        var result = trackName.hashCode()
        result = 31 * result + artistName.hashCode()
        result = 31 * result + trackTimeMillis.hashCode()
        result = 31 * result + (artworkUrl100?.hashCode() ?: 0)
        result = 31 * result + trackId.hashCode()
        result = 31 * result + (collectionName?.hashCode() ?: 0)
        result = 31 * result + (releaseDate?.hashCode() ?: 0)
        result = 31 * result + primaryGenreName.hashCode()
        result = 31 * result + country.hashCode()
        result = 31 * result + (previewUrl?.hashCode() ?: 0)
        return result
    }
}