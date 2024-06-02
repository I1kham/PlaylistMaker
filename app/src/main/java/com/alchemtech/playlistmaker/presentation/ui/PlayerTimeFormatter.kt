package com.alchemtech.playlistmaker.presentation.ui

import java.text.SimpleDateFormat
import java.util.Locale

object PlayerTimeFormatter {
    fun format(int: Int): String{
        return SimpleDateFormat(
            "mm:ss",
            Locale.getDefault()
        ).format(int)
    }
}