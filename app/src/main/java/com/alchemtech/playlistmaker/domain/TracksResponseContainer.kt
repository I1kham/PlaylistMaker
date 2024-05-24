package com.alchemtech.playlistmaker.domain

import com.alchemtech.playlistmaker.domain.models.Track
/*наверное снесем ибо изначально класс нужен был дл возможности передачи кода ответа
* от сервера в слой презентатион, с введением проверки наличия тырнета необходимость отпала*/
data class TracksResponseContainer(
    val tracksList: List<Track>,
    val responseResultCode : Int
)
