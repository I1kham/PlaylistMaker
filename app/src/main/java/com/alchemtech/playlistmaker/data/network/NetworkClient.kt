package com.alchemtech.playlistmaker.data.network

import com.alchemtech.playlistmaker.data.dto.response.Response
/*todo объект заготовка для хранения метода запроса возвращает
*  объект ответа от сервера*/
interface NetworkClient {
    fun doRequest(dto: Any): Response
}