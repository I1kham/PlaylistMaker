package com.alchemtech.playlistmaker.presentation.mediaLibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.alchemtech.playlistmaker.R

class MediaLibActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_library)

        val back = findViewById<TextView>(R.id.media_Lib_text_View)
        back.setOnClickListener {
            finish()
        }



    }
}