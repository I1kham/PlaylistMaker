package com.alchemtech.playlistmaker.presentation.ui

import java.text.SimpleDateFormat
import java.util.Locale

    fun playerTimeFormatter(int: Int): String{
        return SimpleDateFormat(
            "mm:ss",
            Locale.getDefault()
        ).format(int)
    }

fun Long.durationFormatter(): String{
    return SimpleDateFormat(
        "m",
        Locale.getDefault()
    ).format(this)
}