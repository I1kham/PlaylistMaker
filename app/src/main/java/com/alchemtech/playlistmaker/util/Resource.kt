package com.alchemtech.playlistmaker.util

sealed class Resource<T>(val data: T? = null, val message: Int? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(responseCode: Int, data: T? = null) : Resource<T>(data, responseCode)
}