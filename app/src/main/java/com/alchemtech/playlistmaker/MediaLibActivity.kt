package com.alchemtech.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

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