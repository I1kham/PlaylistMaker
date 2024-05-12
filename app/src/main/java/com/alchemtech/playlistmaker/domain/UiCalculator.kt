package com.alchemtech.playlistmaker.domain

import android.content.Context
import android.util.TypedValue

interface UiCalculator {

    fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics).toInt()
    }
}